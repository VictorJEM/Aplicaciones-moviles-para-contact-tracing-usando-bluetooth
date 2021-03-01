from cpu import Cpu

#Nombre del paquete de la aplicacion ASI Ecuador
package = "ec.gob.asi.android"

#Creando objeto, tiempo de ejecucion y dibujo de la serie de tiempo
asi_cpu = Cpu()
tiempo_estimado = int(input("Ingrese el tiempo de prueba: "))
asi_cpu.plot_cpu_real_time(tiempo_estimado, package)

#Generando el .csv
asi_cpu.put_csv()

print("Valor maximo: " + str(asi_cpu.get_max_value()) + " %")
print("Valor minimo: " + str(asi_cpu.get_min_value()) + " %")
print("Mediana: " + str(asi_cpu.get_mediana_value()) + " %")
print("Promedio: " + str(round(asi_cpu.get_promedio_value(), 2)) + " %")
