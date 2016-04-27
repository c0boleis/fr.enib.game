
import sys
import pickle

import pyglet

from pyglet.window import *

# Creation de la fenetre
largeur = 1024
hauteur = 600
window = pyglet.window.Window(largeur, hauteur)

topics = None


class Topic : 

	def __init__(self,nom,listeNomsMotsCle):
		self.nom    = nom
		self.label    = pyglet.text.Label(text=self.nom,x=10,y=550,color=(255,0,0,255),font_size=18)
		self.image    = pyglet.resource.image(nom+'.jpg') 
		self.listeMotsCle = ListeMotsCle(listeNomsMotsCle)

	def draw(self):
		pass


class Topics  : 

	def __init__(self, listeNomsMotsCles) : 
		self.listeNomsMotsCles  = listeNomsMotsCle
		self.indiceTopicCourant = -1
		self.topicCourant       = None
		self.topics             = {}
		self.nombreTopics       = 0

	def ajouter(self,listeNomsTopics):
		for x in listeNomsTopics : 
			self.topics[x] = Topic(x,self.listeNomsMotsCle)
			self.nombreTopics += 1
		if self.topicCourant == None :
			self.topicCourant = (self.topics.values())[0]
			self.indiceTopicCourant = 0

	def draw(self):
		if self.topicCourant != None :
			self.topicCourant.draw()

