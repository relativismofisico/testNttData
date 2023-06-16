CREATE TABLE public.equipments(
  idEquipment       			SERIAL NOT NULL,
  serialCodeEquipment           VARCHAR(250) NOT NULL,
  nameEquipment		            VARCHAR(100) NOT NULL,
  age               INTEGER NOT NULL,
  descriptionEquipment		    VARCHAR(10),
  dateBuyEquipment           	DATE,
  dateUpDateEquipment			DATE,
  priceEquipment				DOUBLE,
  priceEquipmentDevaluation		DOUBLE,
  CONSTRAINT equipments_pkey PRIMARY KEY (idEquipment),
  CONSTRAINT equipments_constraint_1 UNIQUE (serialCodeEquipment)
);