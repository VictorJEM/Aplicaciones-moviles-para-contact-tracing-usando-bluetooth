from ppadb.client import Client as AdbClient
import matplotlib.pyplot as plt
import csv
from numpy import median, mean


class Cpu():

    #Constructor de la clase CPU
    def __init__(self):
        self.client = AdbClient(host="127.0.0.1", port=5037)
        self.devices = self.client.devices()
        self.storage_cpu = []

    #Recolecta los datos de CPU usados en cada segundo
    def read_cpu(self, servicio):
        dispositivo = self.devices[0]
        if dispositivo.is_installed(servicio):
            try:
                dispositivo.shell("dumpsys batterystats --reset")
                data_cpu = dispositivo.shell("dumpsys cpuinfo | grep {}".format(servicio))
                data_cpu = list(data_cpu.strip().rstrip().split())
                data_cpu_use = data_cpu[2].replace('%', '').replace('+', '')
                self.storage_cpu.append(float(data_cpu_use)) 
            except IndexError as error:
                data_cpu_use = 0
                self.storage_cpu.append(float(data_cpu_use))
            return float(data_cpu_use)
        
    #Dibuja la serie de tiempo a medida que pasa el tiempo
    def plot_cpu_real_time(self, range, servicio):
        num_veces = 0
        x , y = [], []
        plt.ion()
        plt.xlabel('time (s)')
        plt.ylabel('cpu used (%)')
        plt.ylim(0, 100)
        plt.title('CPU usage vs. Time')
        plt.grid(True)
        while num_veces < range:
            x.append(num_veces + 1)
            y.append(self.read_cpu(servicio))
            plt.plot(x, y, color='b')
            num_veces += 1
            plt.show()
            plt.pause(1)
        plt.ioff()
        #Guarda la figura mostrada
        plt.savefig("fig_cpuinfo.png")

    #Origina el .csv con los datos
    def put_csv(self):
        with open ('info_cpu.csv', mode='w') as info_cpu:
            cpu_writer = csv.writer(info_cpu, delimiter=',')
            for i in range(len(self.storage_cpu)):
                data = [i+1,self.storage_cpu[i]]
                cpu_writer.writerow(data)

    #Obtiene el maximo valor 
    def get_max_value(self):
        return max(self.storage_cpu)
    
    #Obtiene el minimo valor
    def get_min_value(self):
        return min(self.storage_cpu)
    
    #Obtiene la mediana de los datos recolectados
    def get_mediana_value(self):
        return median(self.storage_cpu)

    #Obtiene el promedio de los datos recolectados
    def get_promedio_value(self):
        return mean(self.storage_cpu) 
    