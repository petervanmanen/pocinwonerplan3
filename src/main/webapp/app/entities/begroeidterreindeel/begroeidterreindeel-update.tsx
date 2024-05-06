import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBegroeidterreindeel } from 'app/shared/model/begroeidterreindeel.model';
import { getEntity, updateEntity, createEntity, reset } from './begroeidterreindeel.reducer';

export const BegroeidterreindeelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const begroeidterreindeelEntity = useAppSelector(state => state.begroeidterreindeel.entity);
  const loading = useAppSelector(state => state.begroeidterreindeel.loading);
  const updating = useAppSelector(state => state.begroeidterreindeel.updating);
  const updateSuccess = useAppSelector(state => state.begroeidterreindeel.updateSuccess);

  const handleClose = () => {
    navigate('/begroeidterreindeel');
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
      ...begroeidterreindeelEntity,
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
          ...begroeidterreindeelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.begroeidterreindeel.home.createOrEditLabel" data-cy="BegroeidterreindeelCreateUpdateHeading">
            Create or edit a Begroeidterreindeel
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
                <ValidatedField name="id" required readOnly id="begroeidterreindeel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Begroeidterreindeeloptalud"
                id="begroeidterreindeel-begroeidterreindeeloptalud"
                name="begroeidterreindeeloptalud"
                data-cy="begroeidterreindeeloptalud"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheidbegroeidterreindeel"
                id="begroeidterreindeel-datumbegingeldigheidbegroeidterreindeel"
                name="datumbegingeldigheidbegroeidterreindeel"
                data-cy="datumbegingeldigheidbegroeidterreindeel"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidbegroeidterreindeel"
                id="begroeidterreindeel-datumeindegeldigheidbegroeidterreindeel"
                name="datumeindegeldigheidbegroeidterreindeel"
                data-cy="datumeindegeldigheidbegroeidterreindeel"
                type="date"
              />
              <ValidatedField
                label="Fysiekvoorkomenbegroeidterreindeel"
                id="begroeidterreindeel-fysiekvoorkomenbegroeidterreindeel"
                name="fysiekvoorkomenbegroeidterreindeel"
                data-cy="fysiekvoorkomenbegroeidterreindeel"
                type="text"
              />
              <ValidatedField
                label="Geometriebegroeidterreindeel"
                id="begroeidterreindeel-geometriebegroeidterreindeel"
                name="geometriebegroeidterreindeel"
                data-cy="geometriebegroeidterreindeel"
                type="text"
              />
              <ValidatedField
                label="Identificatiebegroeidterreindeel"
                id="begroeidterreindeel-identificatiebegroeidterreindeel"
                name="identificatiebegroeidterreindeel"
                data-cy="identificatiebegroeidterreindeel"
                type="text"
              />
              <ValidatedField
                label="Kruinlijngeometriebegroeidterreindeel"
                id="begroeidterreindeel-kruinlijngeometriebegroeidterreindeel"
                name="kruinlijngeometriebegroeidterreindeel"
                data-cy="kruinlijngeometriebegroeidterreindeel"
                type="text"
              />
              <ValidatedField
                label="Lod 0 Geometriebegroeidterreindeel"
                id="begroeidterreindeel-lod0geometriebegroeidterreindeel"
                name="lod0geometriebegroeidterreindeel"
                data-cy="lod0geometriebegroeidterreindeel"
                type="text"
              />
              <ValidatedField
                label="Plusfysiekvoorkomenbegroeidterreindeel"
                id="begroeidterreindeel-plusfysiekvoorkomenbegroeidterreindeel"
                name="plusfysiekvoorkomenbegroeidterreindeel"
                data-cy="plusfysiekvoorkomenbegroeidterreindeel"
                type="text"
              />
              <ValidatedField
                label="Relatievehoogteliggingbegroeidterreindeel"
                id="begroeidterreindeel-relatievehoogteliggingbegroeidterreindeel"
                name="relatievehoogteliggingbegroeidterreindeel"
                data-cy="relatievehoogteliggingbegroeidterreindeel"
                type="text"
              />
              <ValidatedField
                label="Statusbegroeidterreindeel"
                id="begroeidterreindeel-statusbegroeidterreindeel"
                name="statusbegroeidterreindeel"
                data-cy="statusbegroeidterreindeel"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/begroeidterreindeel" replace color="info">
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

export default BegroeidterreindeelUpdate;
