import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInrichtingselement } from 'app/shared/model/inrichtingselement.model';
import { getEntity, updateEntity, createEntity, reset } from './inrichtingselement.reducer';

export const InrichtingselementUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const inrichtingselementEntity = useAppSelector(state => state.inrichtingselement.entity);
  const loading = useAppSelector(state => state.inrichtingselement.loading);
  const updating = useAppSelector(state => state.inrichtingselement.updating);
  const updateSuccess = useAppSelector(state => state.inrichtingselement.updateSuccess);

  const handleClose = () => {
    navigate('/inrichtingselement');
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
      ...inrichtingselementEntity,
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
          ...inrichtingselementEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.inrichtingselement.home.createOrEditLabel" data-cy="InrichtingselementCreateUpdateHeading">
            Create or edit a Inrichtingselement
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
                <ValidatedField name="id" required readOnly id="inrichtingselement-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheidinrichtingselement"
                id="inrichtingselement-datumbegingeldigheidinrichtingselement"
                name="datumbegingeldigheidinrichtingselement"
                data-cy="datumbegingeldigheidinrichtingselement"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidinrichtingselement"
                id="inrichtingselement-datumeindegeldigheidinrichtingselement"
                name="datumeindegeldigheidinrichtingselement"
                data-cy="datumeindegeldigheidinrichtingselement"
                type="date"
              />
              <ValidatedField
                label="Geometrieinrichtingselement"
                id="inrichtingselement-geometrieinrichtingselement"
                name="geometrieinrichtingselement"
                data-cy="geometrieinrichtingselement"
                type="text"
              />
              <ValidatedField
                label="Identificatieinrichtingselement"
                id="inrichtingselement-identificatieinrichtingselement"
                name="identificatieinrichtingselement"
                data-cy="identificatieinrichtingselement"
                type="text"
              />
              <ValidatedField
                label="Lod 0 Geometrieinrichtingselement"
                id="inrichtingselement-lod0geometrieinrichtingselement"
                name="lod0geometrieinrichtingselement"
                data-cy="lod0geometrieinrichtingselement"
                type="text"
              />
              <ValidatedField
                label="Plustypeinrichtingselement"
                id="inrichtingselement-plustypeinrichtingselement"
                name="plustypeinrichtingselement"
                data-cy="plustypeinrichtingselement"
                type="text"
              />
              <ValidatedField
                label="Relatievehoogteligginginrichtingselement"
                id="inrichtingselement-relatievehoogteligginginrichtingselement"
                name="relatievehoogteligginginrichtingselement"
                data-cy="relatievehoogteligginginrichtingselement"
                type="text"
              />
              <ValidatedField
                label="Statusinrichtingselement"
                id="inrichtingselement-statusinrichtingselement"
                name="statusinrichtingselement"
                data-cy="statusinrichtingselement"
                type="text"
              />
              <ValidatedField
                label="Typeinrichtingselement"
                id="inrichtingselement-typeinrichtingselement"
                name="typeinrichtingselement"
                data-cy="typeinrichtingselement"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inrichtingselement" replace color="info">
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

export default InrichtingselementUpdate;
