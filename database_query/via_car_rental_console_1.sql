create schema via_car_rental;
set schema 'via_car_rental';

create table employee
(
    employeeId integer not null unique
        primary key,
    name       varchar(100),
    role       varchar(50)
);

alter table employee
    owner to postgres;

create table drivingLicense
(
    drivingLicenseNumber varchar(20) not null unique
        primary key,
    categoryA            boolean,
    categoryB            boolean,
    categoryC            boolean,
    categoryD            boolean,
    categoryX            boolean
);

alter table drivingLicense
    owner to postgres;

create table customer
(
    customerId    integer not null
        primary key,
    name          varchar(100),
    phoneNo       varchar(20),
    email         varchar(100),
    nationality   varchar(50),
    dob           date,
    driverLicense varchar(20),
    cpr           varchar(20),
    passNo        varchar(20),
    constraint customer_cpr_or_passport_check
        check ((cpr IS NOT NULL) OR (passNo IS NOT NULL)),
    foreign key (driverLicense) references drivingLicense
);

alter table customer
    owner to postgres;

create table vehicle
(
    model                   varchar(50),
    color                   varchar(30),
    engineType              varchar(50),
    plateNumber             varchar(20) not null
        primary key,
    pricePerDay             numeric(10, 2),
    deposit                 numeric(10, 2),
    requiredLicenseCategory varchar(10),
    vehicleStatus           varchar(20),
    numberOfSeats           integer,
    lateFee                 numeric(10, 2),
    brand                   varchar(50),
    type                    varchar(30),
    added_by_employee_Id    integer,
    foreign key (added_by_employee_Id) references employee(employeeId)
);

alter table vehicle
    owner to postgres;

create table sportsCar
(
    plateNumber varchar(20) not null
        primary key
        references vehicle,
    topSpeed    integer,
    turboEngine boolean,
    foreign key (plateNumber) references vehicle(plateNumber)
);

alter table sportsCar
    owner to postgres;

create table motorcycle
(
    plateNumber varchar(20) not null
        primary key
        references vehicle,
    sidecar     boolean,
    foreign key (plateNumber) references vehicle(plateNumber)
);

alter table motorcycle
    owner to postgres;

create table van
(
    plateNumber    varchar(20) not null
        primary key
        references vehicle,
    cargoVolume    numeric(10, 2),
    hasSlidingDoor boolean,
    foreign key (plateNumber) references vehicle(plateNumber)
);

alter table van
    owner to postgres;

create table truck
(
    plateNumber     varchar(20) not null
        primary key
        references vehicle,
    loadCapacity    numeric(10, 2),
    trailerAttached boolean,
    foreign key (plateNumber) references vehicle(plateNumber)
);

alter table truck
    owner to postgres;

create table ufo
(
    plateNumber      varchar(20) not null
        primary key
        references vehicle,
    antiGravityLevel integer,
    isCloakingDevice boolean,
    foreign key (plateNumber) references vehicle(plateNumber)
);

alter table ufo
    owner to postgres;

create table booking
(
    bookingId  integer not null
        primary key,
    customerId integer
        references customer,
    vehicleId  varchar(20)
        references vehicle,
    startDate  date,
    endDate    date,
    employeeId int,
    foreign key (customerId) references customer(customerId),
    foreign key (vehicleId) references vehicle(plateNumber),
    foreign key (employeeId) references employee(employeeId)
);

alter table booking
    owner to postgres;

ALTER TABLE booking
    ADD COLUMN active BOOLEAN DEFAULT TRUE;

ALTER TABLE booking
    ADD COLUMN finalpayment NUMERIC(10, 2) DEFAULT 0.0;

ALTER TABLE booking ALTER COLUMN employeeid DROP NOT NULL;