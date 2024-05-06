import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOndersteunendwegdeel } from 'app/shared/model/ondersteunendwegdeel.model';
import { getEntity, updateEntity, createEntity, reset } from './ondersteunendwegdeel.reducer';

export const OndersteunendwegdeelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ondersteunendwegdeelEntity = useAppSelector(state => state.ondersteunendwegdeel.entity);
  const loading = useAppSelector(state => state.ondersteunendwegdeel.loading);
  const updating = useAppSelector(state => state.ondersteunendwegdeel.updating);
  const updateSuccess = useAppSelector(state => state.ondersteunendwegdeel.updateSuccess);

  const handleClose = () => {
    navigate('/ondersteunendwegdeel');
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

    const entity = {
      ...ondersteunendwegdeelEntity,
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
          ...ondersteunendwegdeelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.ondersteunendwegdeel.home.createOrEditLabel" data-cy="OndersteunendwegdeelCreateUpdateHeading">
            Create or edit a Ondersteunendwegdeel
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="ondersteunendwegdeel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheidondersteunendwegdeel"
                id="ondersteunendwegdeel-datumbegingeldigheidondersteunendwegdeel"
                name="datumbegingeldigheidondersteunendwegdeel"
                data-cy="datumbegingeldigheidondersteunendwegdeel"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidondersteunendwegdeel"
                id="ondersteunendwegdeel-datumeindegeldigheidondersteunendwegdeel"
                name="datumeindegeldigheidondersteunendwegdeel"
                data-cy="datumeindegeldigheidondersteunendwegdeel"
                type="date"
              />
              <ValidatedField
                label="Functieondersteunendwegdeel"
                id="ondersteunendwegdeel-functieondersteunendwegdeel"
                name="functieondersteunendwegdeel"
                data-cy="functieondersteunendwegdeel"
                type="text"
              />
              <ValidatedField
                label="Fysiekvoorkomenondersteunendwegdeel"
                id="ondersteunendwegdeel-fysiekvoorkomenondersteunendwegdeel"
                name="fysiekvoorkomenondersteunendwegdeel"
                data-cy="fysiekvoorkomenondersteunendwegdeel"
                type="text"
              />
              <ValidatedField
                label="Geometrieondersteunendwegdeel"
                id="ondersteunendwegdeel-geometrieondersteunendwegdeel"
                name="geometrieondersteunendwegdeel"
                data-cy="geometrieondersteunendwegdeel"
                type="text"
              />
              <ValidatedField
                label="Identificatieondersteunendwegdeel"
                id="ondersteunendwegdeel-identificatieondersteunendwegdeel"
                name="identificatieondersteunendwegdeel"
                data-cy="identificatieondersteunendwegdeel"
                type="text"
              />
              <ValidatedField
                label="Kruinlijngeometrieondersteunendwegdeel"
                id="ondersteunendwegdeel-kruinlijngeometrieondersteunendwegdeel"
                name="kruinlijngeometrieondersteunendwegdeel"
                data-cy="kruinlijngeometrieondersteunendwegdeel"
                type="text"
              />
              <ValidatedField
                label="Lod 0 Geometrieondersteunendwegdeel"
                id="ondersteunendwegdeel-lod0geometrieondersteunendwegdeel"
                name="lod0geometrieondersteunendwegdeel"
                data-cy="lod0geometrieondersteunendwegdeel"
                type="text"
              />
              <ValidatedField
                label="Ondersteunendwegdeeloptalud"
                id="ondersteunendwegdeel-ondersteunendwegdeeloptalud"
                name="ondersteunendwegdeeloptalud"
                data-cy="ondersteunendwegdeeloptalud"
                type="text"
              />
              <ValidatedField
                label="Plusfunctieondersteunendwegdeel"
                id="ondersteunendwegdeel-plusfunctieondersteunendwegdeel"
                name="plusfunctieondersteunendwegdeel"
                data-cy="plusfunctieondersteunendwegdeel"
                type="text"
              />
              <ValidatedField
                label="Plusfysiekvoorkomenondersteunendwegdeel"
                id="ondersteunendwegdeel-plusfysiekvoorkomenondersteunendwegdeel"
                name="plusfysiekvoorkomenondersteunendwegdeel"
                data-cy="plusfysiekvoorkomenondersteunendwegdeel"
                type="text"
              />
              <ValidatedField
                label="Relatievehoogteliggingondersteunendwegdeel"
                id="ondersteunendwegdeel-relatievehoogteliggingondersteunendwegdeel"
                name="relatievehoogteliggingondersteunendwegdeel"
                data-cy="relatievehoogteliggingondersteunendwegdeel"
                type="text"
              />
              <ValidatedField
                label="Statusondersteunendwegdeel"
                id="ondersteunendwegdeel-statusondersteunendwegdeel"
                name="statusondersteunendwegdeel"
                data-cy="statusondersteunendwegdeel"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ondersteunendwegdeel" replace color="info">
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

export default OndersteunendwegdeelUpdate;
