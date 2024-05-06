import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOnbegroeidterreindeel } from 'app/shared/model/onbegroeidterreindeel.model';
import { getEntity, updateEntity, createEntity, reset } from './onbegroeidterreindeel.reducer';

export const OnbegroeidterreindeelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const onbegroeidterreindeelEntity = useAppSelector(state => state.onbegroeidterreindeel.entity);
  const loading = useAppSelector(state => state.onbegroeidterreindeel.loading);
  const updating = useAppSelector(state => state.onbegroeidterreindeel.updating);
  const updateSuccess = useAppSelector(state => state.onbegroeidterreindeel.updateSuccess);

  const handleClose = () => {
    navigate('/onbegroeidterreindeel');
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
      ...onbegroeidterreindeelEntity,
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
          ...onbegroeidterreindeelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.onbegroeidterreindeel.home.createOrEditLabel" data-cy="OnbegroeidterreindeelCreateUpdateHeading">
            Create or edit a Onbegroeidterreindeel
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
                <ValidatedField name="id" required readOnly id="onbegroeidterreindeel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheidonbegroeidterreindeel"
                id="onbegroeidterreindeel-datumbegingeldigheidonbegroeidterreindeel"
                name="datumbegingeldigheidonbegroeidterreindeel"
                data-cy="datumbegingeldigheidonbegroeidterreindeel"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidonbegroeidterreindeel"
                id="onbegroeidterreindeel-datumeindegeldigheidonbegroeidterreindeel"
                name="datumeindegeldigheidonbegroeidterreindeel"
                data-cy="datumeindegeldigheidonbegroeidterreindeel"
                type="date"
              />
              <ValidatedField
                label="Fysiekvoorkomenonbegroeidterreindeel"
                id="onbegroeidterreindeel-fysiekvoorkomenonbegroeidterreindeel"
                name="fysiekvoorkomenonbegroeidterreindeel"
                data-cy="fysiekvoorkomenonbegroeidterreindeel"
                type="text"
              />
              <ValidatedField
                label="Geometrieonbegroeidterreindeel"
                id="onbegroeidterreindeel-geometrieonbegroeidterreindeel"
                name="geometrieonbegroeidterreindeel"
                data-cy="geometrieonbegroeidterreindeel"
                type="text"
              />
              <ValidatedField
                label="Identificatieonbegroeidterreindeel"
                id="onbegroeidterreindeel-identificatieonbegroeidterreindeel"
                name="identificatieonbegroeidterreindeel"
                data-cy="identificatieonbegroeidterreindeel"
                type="text"
              />
              <ValidatedField
                label="Kruinlijngeometrieonbegroeidterreindeel"
                id="onbegroeidterreindeel-kruinlijngeometrieonbegroeidterreindeel"
                name="kruinlijngeometrieonbegroeidterreindeel"
                data-cy="kruinlijngeometrieonbegroeidterreindeel"
                type="text"
              />
              <ValidatedField
                label="Onbegroeidterreindeeloptalud"
                id="onbegroeidterreindeel-onbegroeidterreindeeloptalud"
                name="onbegroeidterreindeeloptalud"
                data-cy="onbegroeidterreindeeloptalud"
                type="text"
              />
              <ValidatedField
                label="Plusfysiekvoorkomenonbegroeidterreindeel"
                id="onbegroeidterreindeel-plusfysiekvoorkomenonbegroeidterreindeel"
                name="plusfysiekvoorkomenonbegroeidterreindeel"
                data-cy="plusfysiekvoorkomenonbegroeidterreindeel"
                type="text"
              />
              <ValidatedField
                label="Relatievehoogteliggingonbegroeidterreindeel"
                id="onbegroeidterreindeel-relatievehoogteliggingonbegroeidterreindeel"
                name="relatievehoogteliggingonbegroeidterreindeel"
                data-cy="relatievehoogteliggingonbegroeidterreindeel"
                type="text"
              />
              <ValidatedField
                label="Statusonbegroeidterreindeel"
                id="onbegroeidterreindeel-statusonbegroeidterreindeel"
                name="statusonbegroeidterreindeel"
                data-cy="statusonbegroeidterreindeel"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/onbegroeidterreindeel" replace color="info">
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

export default OnbegroeidterreindeelUpdate;
