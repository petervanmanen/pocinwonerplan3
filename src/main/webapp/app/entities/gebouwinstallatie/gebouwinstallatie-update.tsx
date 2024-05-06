import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGebouwinstallatie } from 'app/shared/model/gebouwinstallatie.model';
import { getEntity, updateEntity, createEntity, reset } from './gebouwinstallatie.reducer';

export const GebouwinstallatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const gebouwinstallatieEntity = useAppSelector(state => state.gebouwinstallatie.entity);
  const loading = useAppSelector(state => state.gebouwinstallatie.loading);
  const updating = useAppSelector(state => state.gebouwinstallatie.updating);
  const updateSuccess = useAppSelector(state => state.gebouwinstallatie.updateSuccess);

  const handleClose = () => {
    navigate('/gebouwinstallatie');
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
      ...gebouwinstallatieEntity,
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
          ...gebouwinstallatieEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.gebouwinstallatie.home.createOrEditLabel" data-cy="GebouwinstallatieCreateUpdateHeading">
            Create or edit a Gebouwinstallatie
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
                <ValidatedField name="id" required readOnly id="gebouwinstallatie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheidgebouwinstallatie"
                id="gebouwinstallatie-datumbegingeldigheidgebouwinstallatie"
                name="datumbegingeldigheidgebouwinstallatie"
                data-cy="datumbegingeldigheidgebouwinstallatie"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidgebouwinstallatie"
                id="gebouwinstallatie-datumeindegeldigheidgebouwinstallatie"
                name="datumeindegeldigheidgebouwinstallatie"
                data-cy="datumeindegeldigheidgebouwinstallatie"
                type="date"
              />
              <ValidatedField
                label="Geometriegebouwinstallatie"
                id="gebouwinstallatie-geometriegebouwinstallatie"
                name="geometriegebouwinstallatie"
                data-cy="geometriegebouwinstallatie"
                type="text"
              />
              <ValidatedField
                label="Identificatiegebouwinstallatie"
                id="gebouwinstallatie-identificatiegebouwinstallatie"
                name="identificatiegebouwinstallatie"
                data-cy="identificatiegebouwinstallatie"
                type="text"
              />
              <ValidatedField
                label="Lod 0 Geometriegebouwinstallatie"
                id="gebouwinstallatie-lod0geometriegebouwinstallatie"
                name="lod0geometriegebouwinstallatie"
                data-cy="lod0geometriegebouwinstallatie"
                type="text"
              />
              <ValidatedField
                label="Relatievehoogteligginggebouwinstallatie"
                id="gebouwinstallatie-relatievehoogteligginggebouwinstallatie"
                name="relatievehoogteligginggebouwinstallatie"
                data-cy="relatievehoogteligginggebouwinstallatie"
                type="text"
              />
              <ValidatedField
                label="Statusgebouwinstallatie"
                id="gebouwinstallatie-statusgebouwinstallatie"
                name="statusgebouwinstallatie"
                data-cy="statusgebouwinstallatie"
                type="text"
              />
              <ValidatedField
                label="Typegebouwinstallatie"
                id="gebouwinstallatie-typegebouwinstallatie"
                name="typegebouwinstallatie"
                data-cy="typegebouwinstallatie"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gebouwinstallatie" replace color="info">
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

export default GebouwinstallatieUpdate;
