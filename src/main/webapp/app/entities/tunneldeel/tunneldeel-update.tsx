import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITunneldeel } from 'app/shared/model/tunneldeel.model';
import { getEntity, updateEntity, createEntity, reset } from './tunneldeel.reducer';

export const TunneldeelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tunneldeelEntity = useAppSelector(state => state.tunneldeel.entity);
  const loading = useAppSelector(state => state.tunneldeel.loading);
  const updating = useAppSelector(state => state.tunneldeel.updating);
  const updateSuccess = useAppSelector(state => state.tunneldeel.updateSuccess);

  const handleClose = () => {
    navigate('/tunneldeel');
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
      ...tunneldeelEntity,
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
          ...tunneldeelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.tunneldeel.home.createOrEditLabel" data-cy="TunneldeelCreateUpdateHeading">
            Create or edit a Tunneldeel
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="tunneldeel-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumbegingeldigheidtunneldeel"
                id="tunneldeel-datumbegingeldigheidtunneldeel"
                name="datumbegingeldigheidtunneldeel"
                data-cy="datumbegingeldigheidtunneldeel"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidtunneldeel"
                id="tunneldeel-datumeindegeldigheidtunneldeel"
                name="datumeindegeldigheidtunneldeel"
                data-cy="datumeindegeldigheidtunneldeel"
                type="date"
              />
              <ValidatedField
                label="Geometrietunneldeel"
                id="tunneldeel-geometrietunneldeel"
                name="geometrietunneldeel"
                data-cy="geometrietunneldeel"
                type="text"
              />
              <ValidatedField
                label="Identificatietunneldeel"
                id="tunneldeel-identificatietunneldeel"
                name="identificatietunneldeel"
                data-cy="identificatietunneldeel"
                type="text"
              />
              <ValidatedField
                label="Lod 0 Geometrietunneldeel"
                id="tunneldeel-lod0geometrietunneldeel"
                name="lod0geometrietunneldeel"
                data-cy="lod0geometrietunneldeel"
                type="text"
              />
              <ValidatedField
                label="Relatievehoogteliggingtunneldeel"
                id="tunneldeel-relatievehoogteliggingtunneldeel"
                name="relatievehoogteliggingtunneldeel"
                data-cy="relatievehoogteliggingtunneldeel"
                type="text"
              />
              <ValidatedField
                label="Statustunneldeel"
                id="tunneldeel-statustunneldeel"
                name="statustunneldeel"
                data-cy="statustunneldeel"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tunneldeel" replace color="info">
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

export default TunneldeelUpdate;
