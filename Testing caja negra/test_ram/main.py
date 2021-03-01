from ram_memory import MemoryRam

#Nombre del paquete de la aplicacion ASI Ecuador
package = "ec.gob.asi.android"

#Creando objeto, tiempo de ejecucion y dibujo de la serie de tiempo
asi_ram = MemoryRam()
tiempo_esperado = int(input("Ingrese tiempo para realizar la prueba: "))
asi_ram.plot_ram(tiempo_esperado, package)

#Generando el .csv
asi_ram.put_csv()


print("Valor maximo: " + str(asi_ram.get_max_value()) + " MB")
print("Valor minimo: " + str(asi_ram.get_min_value()) + " MB")
print("Mediana: " + str(asi_ram.get_mediana_value()) + " MB")
print("Promedio: " + str(asi_ram.get_promedio_value()) + " MB")


