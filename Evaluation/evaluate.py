import pandas as pd
import glob
import sys
import os
import matplotlib.pyplot as plt
store = pd.HDFStore('store.h5')
threadAgg = [("sizeOpCount", "sizeOps"), ("sizeDuration", "avgSizeDuration")]
linespecs = ['bo-', 'go-', 'ro-']
type_legend = ("Unwrapped", "SemiSizable", "Sizable")
load_type_legend = ("Type: SemiSizable Load: 20i-10d-20s", "Type: Sizable Load: 20i-10d-20s", "Type: SemiSizable Load: 33i-33d-34s", "Type: Sizable Load: 33i-33d-34s", "Type: SemiSizable Load: 45i-45d-10s", "Type: Sizable Load: 45i-45d-10s")
load_type_legend2 = ("Type: SemiSizable Load: 33i-33d-34s", "Type: Sizable Load: 33i-33d-34s", "Type: SemiSizable Load: 33i-33d-0s", "Type: Sizable Load: 33i-33d-0s")
algsDict = [
    {
        "name": "SkipList",
        "run_names": ['SkipList', 'SemiSizeableSkipList', 'SizeableSkipList'],
    },
    {
        "name": "HashMap",
        "run_names": ['ConcurrentHMAP', 'SemiSizeableHMAP', 'SizeableHMAP'],
    },
    {
        "name": "AVLTree",
        "run_names": ['LockFreeAVL', 'SemiSizeableLFAVL', 'SizeableLFAVL']
    }
]
threanums = [1, 2, 4, 8, 16, 32]
keynums = [100, 10000, 1000000]
load_types =["20i-10d-20s", "33i-33d-34s", "45i-45d-10s"]


def load():
    print("loading data")
    path = r'result'  # use your path
    allFiles = glob.glob(path + "/*.csv")
    curr_df = pd.DataFrame()
    list_ = []
    for file_ in allFiles:
        curr_df = pd.read_csv(file_, index_col=None, header=0)
        list_.append(curr_df)
    to_save_df = pd.concat(list_)
    store['df'] = to_save_df
    print("saved new data")


def get_size_time(g):
    th_num = g["nthreads"][0]
    inds = ["thread" + str(i) + "avgSizeDuration" for i in range(th_num)]
    return g[inds].mean(axis=1).mean()


def get_size_ops(g):
    th_num = g["nthreads"][0]
    inds = ["thread" + str(i) + "sizeOps" for i in range(th_num)]
    return g[inds].mean(axis=1).mean()


def get_mutation_time(g):
    return (10 - get_size_time(g)*get_size_ops(g))*g["nthreads"][0] / g["throughput"].mean()


if __name__ == "__main__":
    if len(sys.argv) > 1 and sys.argv[1] == "load" or "df" not in store:
        load()
    df = store['df']
    grouped = df.groupby(["name", "ratio", "nthreads", "maxkey"])
    for alg in algsDict:
        # Wrapper Throughput test
        for alg_type in alg["run_names"]:
            p = [grouped.get_group((alg_type, "50i-50d-0s", th, keynums[-1]))["threadops"].mean()/th for th in threanums]
            plt.plot(threanums, p, marker='o')
        plt.title(alg["name"] + ": Thread ops in wrapper")
        plt.ylabel("Average thread ops [N]")
        plt.xlabel("Thread number [N]")
        plt.legend(type_legend, loc='lower left')
        plt.savefig(os.path.join("plots", alg["name"] + "_wrapper_throughput"))
        plt.close()

        # Size duration test
        for lt in load_types:
            computation_overhead = [get_size_time(grouped.get_group((alg_type, load_types[0], th, keynums[-1]))) for th in threanums]
            for alg_type in alg["run_names"][1:]:
                p = [1.e6*get_size_time(grouped.get_group((alg_type, lt, th, keynums[-1])))# - computation_overhead[i]
                     for th in threanums]
                plt.plot(threanums, p, marker='o')
        plt.title(alg["name"] + ": Average Size Time")
        plt.ylabel("Average thread ops [uS]")
        plt.xlabel("Thread number [N]")
        plt.legend(load_type_legend, loc='upper left')
        plt.savefig(os.path.join("plots", alg["name"] + "_size_duration"))
        plt.close()

        # Mutation duration test
        for lt in ["33i-33d-34s", "33i-33d-0s"]:
            for alg_type in alg["run_names"][1:]:
                p = [1.e6 * get_mutation_time(grouped.get_group((alg_type, lt, th, keynums[-1])))
                     for th in threanums]
                plt.plot(threanums, p, marker='o')
        plt.title(alg["name"] + ": Average mutation Time")
        plt.ylabel("Average thread ops [uS]")
        plt.xlabel("Thread number [N]")
        plt.legend(load_type_legend2, loc='upper left')
        plt.savefig(os.path.join("plots", alg["name"] + "_mutation_duration"))
        plt.close()

        # Key range test
        for alg_type in alg["run_names"]:
            p = [grouped.get_group((alg_type, "33i-33d-34s", 16, kr))["threadops"].mean()
                 for kr in keynums]
            plt.plot([2, 3, 6], p, marker='o')
        plt.title(alg["name"] + ": Key Range Throughput")
        plt.ylabel("Average thread ops [uS]")
        plt.xlabel("Key range [10^n]")
        plt.legend(type_legend, loc='lower left')
        plt.savefig(os.path.join("plots", alg["name"] + "_key_range"))
        plt.close()





