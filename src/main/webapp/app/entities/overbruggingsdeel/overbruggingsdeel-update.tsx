import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOverbruggingsdeel } from 'app/shared/model/overbruggingsdeel.model';
import { getEntity, updateEntity, createEntity, reset } from './overbruggingsdeel.reducer';

export const OverbruggingsdeelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const overbruggingsdeelEntity = useAppSelector(state => state.overbruggingsdeel.entity);
  const loading = useAppSelector(state => state.overbruggingsdeel.loading);
  const updating = useAppSelector(state => state.overbruggingsdeel.updating);
  const updateSuccess = useAppSelector(state => state.overbruggingsdeel.updateSuccess);

  const handleClose = () => {
    navigate('/overbruggingsdeel');
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
      ...overbruggingsdeelEntity,
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
          ...overbruggingsdeelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.overbruggingsdeel.home.createOrEditLabel" data-cy="OverbruggingsdeelCreateUpdateHeading">
            Create or edit a Overbruggingsdeel
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
                <ValidatedField name="id" required readOnly id="overbruggingsdeel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheidoverbruggingsdeel"
                id="overbruggingsdeel-datumbegingeldigheidoverbruggingsdeel"
                name="datumbegingeldigheidoverbruggingsdeel"
                data-cy="datumbegingeldigheidoverbruggingsdeel"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidoverbruggingsdeel"
                id="overbruggingsdeel-datumeindegeldigheidoverbruggingsdeel"
                name="datumeindegeldigheidoverbruggingsdeel"
                data-cy="datumeindegeldigheidoverbruggingsdeel"
                type="date"
              />
              <ValidatedField
                label="Geometrieoverbruggingsdeel"
                id="overbruggingsdeel-geometrieoverbruggingsdeel"
                name="geometrieoverbruggingsdeel"
                data-cy="geometrieoverbruggingsdeel"
                type="text"
              />
              <ValidatedField
                label="Hoortbijtypeoverbrugging"
                id="overbruggingsdeel-hoortbijtypeoverbrugging"
                name="hoortbijtypeoverbrugging"
                data-cy="hoortbijtypeoverbrugging"
                type="text"
              />
              <ValidatedField
                label="Identificatieoverbruggingsdeel"
                id="overbruggingsdeel-identificatieoverbruggingsdeel"
                name="identificatieoverbruggingsdeel"
                data-cy="identificatieoverbruggingsdeel"
                type="text"
              />
              <ValidatedField
                label="Lod 0 Geometrieoverbruggingsdeel"
                id="overbruggingsdeel-lod0geometrieoverbruggingsdeel"
                name="lod0geometrieoverbruggingsdeel"
                data-cy="lod0geometrieoverbruggingsdeel"
                type="text"
              />
              <ValidatedField
                label="Overbruggingisbeweegbaar"
                id="overbruggingsdeel-overbruggingisbeweegbaar"
                name="overbruggingisbeweegbaar"
                data-cy="overbruggingisbeweegbaar"
                type="text"
              />
              <ValidatedField
                label="Relatievehoogteliggingoverbruggingsdeel"
                id="overbruggingsdeel-relatievehoogteliggingoverbruggingsdeel"
                name="relatievehoogteliggingoverbruggingsdeel"
                data-cy="relatievehoogteliggingoverbruggingsdeel"
                type="text"
              />
              <ValidatedField
                label="Statusoverbruggingsdeel"
                id="overbruggingsdeel-statusoverbruggingsdeel"
                name="statusoverbruggingsdeel"
                data-cy="statusoverbruggingsdeel"
                type="text"
              />
              <ValidatedField
                label="Typeoverbruggingsdeel"
                id="overbruggingsdeel-typeoverbruggingsdeel"
                name="typeoverbruggingsdeel"
                data-cy="typeoverbruggingsdeel"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/overbruggingsdeel" replace color="info">
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

export default OverbruggingsdeelUpdate;
