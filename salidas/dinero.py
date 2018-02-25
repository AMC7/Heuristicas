def funcion():
	archivo = open('yum.txt').read()
	arreglo = archivo.split('\n')	
	res = []	
	for cadena in arreglo:
		if not 'i' in cadena and len(cadena) > 0:
			res.append(cadena.replace('Path: ',''))
	return res

cadenas = funcion()
i = 0
for i in range(len(cadenas)):
	open('prueba'+str(i)+'.tsp','w').write(cadenas[i])	
