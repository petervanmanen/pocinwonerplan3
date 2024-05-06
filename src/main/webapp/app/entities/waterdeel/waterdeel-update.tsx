import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWaterdeel } from 'app/shared/model/waterdeel.model';
import { getEntity, updateEntity, createEntity, reset } from './waterdeel.reducer';

export const WaterdeelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const waterdeelEntity = useAppSelector(state => state.waterdeel.entity);
  const loading = useAppSelector(state => state.waterdeel.loading);
  const updating = useAppSelector(state => state.waterdeel.updating);
  const updateSuccess = useAppSelector(state => state.waterdeel.updateSuccess);

  const handleClose = () => {
    navigate('/waterdeel');
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
      ...waterdeelEntity,
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
          ...waterdeelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.waterdeel.home.createOrEditLabel" data-cy="WaterdeelCreateUpdateHeading">
            Create or edit a Waterdeel
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="waterdeel-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumbegingeldigheidwaterdeel"
                id="waterdeel-datumbegingeldigheidwaterdeel"
                name="datumbegingeldigheidwaterdeel"
                data-cy="datumbegingeldigheidwaterdeel"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidwaterdeel"
                id="waterdeel-datumeindegeldigheidwaterdeel"
                name="datumeindegeldigheidwaterdeel"
                data-cy="datumeindegeldigheidwaterdeel"
                type="date"
              />
              <ValidatedField
                label="Geometriewaterdeel"
                id="waterdeel-geometriewaterdeel"
                name="geometriewaterdeel"
                data-cy="geometriewaterdeel"
                type="text"
              />
              <ValidatedField
                label="Identificatiewaterdeel"
                id="waterdeel-identificatiewaterdeel"
                name="identificatiewaterdeel"
                data-cy="identificatiewaterdeel"
                type="text"
              />
              <ValidatedField
                label="Plustypewaterdeel"
                id="waterdeel-plustypewaterdeel"
                name="plustypewaterdeel"
                data-cy="plustypewaterdeel"
                type="text"
              />
              <ValidatedField
                label="Relatievehoogteliggingwaterdeel"
                id="waterdeel-relatievehoogteliggingwaterdeel"
                name="relatievehoogteliggingwaterdeel"
                data-cy="relatievehoogteliggingwaterdeel"
                type="text"
              />
              <ValidatedField
                label="Statuswaterdeel"
                id="waterdeel-statuswaterdeel"
                name="statuswaterdeel"
                data-cy="statuswaterdeel"
                type="text"
              />
              <ValidatedField label="Typewaterdeel" id="waterdeel-typewaterdeel" name="typewaterdeel" data-cy="typewaterdeel" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/waterdeel" replace color="info">
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

export default WaterdeelUpdate;
