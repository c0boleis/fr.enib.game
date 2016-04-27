
import sys
import pickle

import pyglet

from pyglet.window import *

# Creation de la fenetre
largeur = 1024
hauteur = 600
window = pyglet.window.Window(largeur, hauteur)

topics = None


class MotCle : 

	def __init__(self):
		pass

	def augmenterImportance(self):
		pass

	def diminuerImportance(self):
		pass

	def draw(self):
		pass


class listeMotsCle : 

	def __init__(self):
		pass

	def getMotCleCourant(self):
		return None

	def draw(self):
		pass


class Topic : 

	def __init(self):
		pass

	def getListeMotsCle(self):
		return None

	def draw(self):
		pass



class Topics : 

	def __init__(self):
		pass

	def draw(self):
		pass

	def getTopicCourant(self):
		return None

	def topicSuivant(self):
		pass

	def topicPrecedent(self):
		pass

	def sauver(self,nomFichier):
		pass

	def charger(self,nomfichier):
		pass

def init():
	global topics
	topics = Topics()

# Callback d affichage
@window.event
def on_draw():
	window.clear()
	topics.draw()

@window.event
def on_key_press(symbol,modifiers):
	global topics
	global indice_courant
	if symbol == pyglet.window.key.RIGHT : 
		topics.suivant()
	elif symbol == pyglet.window.key.LEFT : 
		topics.precedent()
	elif symbol == pyglet.window.key.DOWN :
		topics.getTopicCourant().getListeMotsCle().topicSuivant()
	elif symbol == pyglet.window.key.UP :
		topics.getTopicCourant().getListeMotsCle().topicPrecedent() 
	elif symbol == pyglet.window.key.H:
		topics.getTopicCourant().getListeMotsCle().getMotCleCourant().augmenterImportance()
	elif symbol == pyglet.window.key.B:
		topics.getTopicCourant().getListeMotsCle().getMotCleCourant().diminuerImportance()
	elif symbol == pyglet.window.key.X:
		topics.sauver("saved")
