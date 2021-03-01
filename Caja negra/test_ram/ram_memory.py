from ppadb.client import Client as AdbClient
import matplotlib.pyplot as plt
import csv
from numpy import median, mean


class MemoryRam():

    #Constructor de la clase MemoryRam
    def __init__(self):
        self.client = AdbClient(host="127.0.0.1", port=5037)
        self.devices = self.client.devices()
        self.actual_ram = None
        self.storage_ram = []
    
    #Recolecta los datos de memoria RAM usados en cada segundo
    def read_ram(self, servicio):
        for device in self.devices:
            if device.is_installed(servicio):
                try:
                    data_mem = device.shell("dumpsys meminfo {} | grep TOTAL".format(servicio))
                    data_mem = list(data_mem.strip().rstrip().split())
                    self.actual_ram = int(data_mem[1]) / 1000
                    self.actual_ram = round(self.actual_ram,2)
                    self.storage_ram.append(self.actual_ram)
                except IndexError as error:
                    self.actual_ram = 0
                    self.storage_ram.append(self.actual_ram)
                return self.actual_ram

    #Dibuja la serie de tiempo a medida que pasa el tiempo
    def plot_ram(self, range_time, servicio):
        num_veces = 0
        x , y = [], []
        plt.ion()
        plt.xlabel('time (s)')
        plt.ylabel('memory ram (MB)')
        plt.ylim(0, 256)
        plt.title('Memory Ram usage vs. Time')
        plt.grid(True)
        while num_veces < range_time:
            x.append(num_veces + 1)
            y.append(self.read_ram(servicio))
            plt.plot(x, y, color='b')
            num_veces += 1
            plt.show()
            plt.pause(1)
        plt.ioff()
        #Guarda la figura mostrada
        plt.savefig('fig_raminfo.png')
    
    #Origina el .csv con los datos
    def put_csv(self):
        with open ('info_ram_memory.csv', mode='w') as info_ram:
            ram_writer = csv.writer(info_ram, delimiter=',')
            for i in range(len(self.storage_ram)):
                data = [i+1,self.storage_ram[i]]
                ram_writer.writerow(data)

    #Obtiene el maximo valor 
    def get_max_value(self):
        return max(self.storage_ram)
    
    #Obtiene el minimo valor
    def get_min_value(self):
        return min(self.storage_ram)
    
    #Obtiene la mediana de los datos recolectados
    def get_mediana_value(self):
        return median(self.storage_ram)

    #Obtiene el promedio de los datos recolectados
    def get_promedio_value(self):
        return mean(self.storage_ram)
        



