import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAardfiliatie } from 'app/shared/model/aardfiliatie.model';
import { getEntity, updateEntity, createEntity, reset } from './aardfiliatie.reducer';

export const AardfiliatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aardfiliatieEntity = useAppSelector(state => state.aardfiliatie.entity);
  const loading = useAppSelector(state => state.aardfiliatie.loading);
  const updating = useAppSelector(state => state.aardfiliatie.updating);
  const updateSuccess = useAppSelector(state => state.aardfiliatie.updateSuccess);

  const handleClose = () => {
    navigate('/aardfiliatie');
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
      ...aardfiliatieEntity,
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
          ...aardfiliatieEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aardfiliatie.home.createOrEditLabel" data-cy="AardfiliatieCreateUpdateHeading">
            Create or edit a Aardfiliatie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="aardfiliatie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Codeaardfiliatie"
                id="aardfiliatie-codeaardfiliatie"
                name="codeaardfiliatie"
                data-cy="codeaardfiliatie"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheidaardfiliatie"
                id="aardfiliatie-datumbegingeldigheidaardfiliatie"
                name="datumbegingeldigheidaardfiliatie"
                data-cy="datumbegingeldigheidaardfiliatie"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidaardfiliatie"
                id="aardfiliatie-datumeindegeldigheidaardfiliatie"
                name="datumeindegeldigheidaardfiliatie"
                data-cy="datumeindegeldigheidaardfiliatie"
                type="date"
              />
              <ValidatedField
                label="Naamaardfiliatie"
                id="aardfiliatie-naamaardfiliatie"
                name="naamaardfiliatie"
                data-cy="naamaardfiliatie"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aardfiliatie" replace color="info">
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

export default AardfiliatieUpdate;
