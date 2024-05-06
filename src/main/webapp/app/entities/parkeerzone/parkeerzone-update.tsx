import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IParkeerzone } from 'app/shared/model/parkeerzone.model';
import { getEntity, updateEntity, createEntity, reset } from './parkeerzone.reducer';

export const ParkeerzoneUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const parkeerzoneEntity = useAppSelector(state => state.parkeerzone.entity);
  const loading = useAppSelector(state => state.parkeerzone.loading);
  const updating = useAppSelector(state => state.parkeerzone.updating);
  const updateSuccess = useAppSelector(state => state.parkeerzone.updateSuccess);

  const handleClose = () => {
    navigate('/parkeerzone');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.dagtarief !== undefined && typeof values.dagtarief !== 'number') {
      values.dagtarief = Number(values.dagtarief);
    }
    if (values.starttarief !== undefined && typeof values.starttarief !== 'number') {
      values.starttarief = Number(values.starttarief);
    }
    if (values.uurtarief !== undefined && typeof values.uurtarief !== 'number') {
      values.uurtarief = Number(values.uurtarief);
    }

    const entity = {
      ...parkeerzoneEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...parkeerzoneEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.parkeerzone.home.createOrEditLabel" data-cy="ParkeerzoneCreateUpdateHeading">
            Create or edit a Parkeerzone
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="parkeerzone-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aantalparkeervlakken"
                id="parkeerzone-aantalparkeervlakken"
                name="aantalparkeervlakken"
                data-cy="aantalparkeervlakken"
                type="text"
              />
              <ValidatedField
                label="Alleendagtarief"
                id="parkeerzone-alleendagtarief"
                name="alleendagtarief"
                data-cy="alleendagtarief"
                check
                type="checkbox"
              />
              <ValidatedField label="Dagtarief" id="parkeerzone-dagtarief" name="dagtarief" data-cy="dagtarief" type="text" />
              <ValidatedField
                label="Eindedag"
                id="parkeerzone-eindedag"
                name="eindedag"
                data-cy="eindedag"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Eindtijd" id="parkeerzone-eindtijd" name="eindtijd" data-cy="eindtijd" type="text" />
              <ValidatedField label="Gebruik" id="parkeerzone-gebruik" name="gebruik" data-cy="gebruik" type="text" />
              <ValidatedField label="Geometrie" id="parkeerzone-geometrie" name="geometrie" data-cy="geometrie" type="text" />
              <ValidatedField label="Ipmcode" id="parkeerzone-ipmcode" name="ipmcode" data-cy="ipmcode" type="text" />
              <ValidatedField label="Ipmnaam" id="parkeerzone-ipmnaam" name="ipmnaam" data-cy="ipmnaam" type="text" />
              <ValidatedField label="Naam" id="parkeerzone-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Parkeergarage"
                id="parkeerzone-parkeergarage"
                name="parkeergarage"
                data-cy="parkeergarage"
                check
                type="checkbox"
              />
              <ValidatedField label="Sectorcode" id="parkeerzone-sectorcode" name="sectorcode" data-cy="sectorcode" type="text" />
              <ValidatedField label="Soortcode" id="parkeerzone-soortcode" name="soortcode" data-cy="soortcode" type="text" />
              <ValidatedField
                label="Startdag"
                id="parkeerzone-startdag"
                name="startdag"
                data-cy="startdag"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Starttarief" id="parkeerzone-starttarief" name="starttarief" data-cy="starttarief" type="text" />
              <ValidatedField label="Starttijd" id="parkeerzone-starttijd" name="starttijd" data-cy="starttijd" type="text" />
              <ValidatedField label="Typecode" id="parkeerzone-typecode" name="typecode" data-cy="typecode" type="text" />
              <ValidatedField label="Typenaam" id="parkeerzone-typenaam" name="typenaam" data-cy="typenaam" type="text" />
              <ValidatedField label="Uurtarief" id="parkeerzone-uurtarief" name="uurtarief" data-cy="uurtarief" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/parkeerzone" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ParkeerzoneUpdate;
