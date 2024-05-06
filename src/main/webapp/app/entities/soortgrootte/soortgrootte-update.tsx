import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISoortgrootte } from 'app/shared/model/soortgrootte.model';
import { getEntity, updateEntity, createEntity, reset } from './soortgrootte.reducer';

export const SoortgrootteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const soortgrootteEntity = useAppSelector(state => state.soortgrootte.entity);
  const loading = useAppSelector(state => state.soortgrootte.loading);
  const updating = useAppSelector(state => state.soortgrootte.updating);
  const updateSuccess = useAppSelector(state => state.soortgrootte.updateSuccess);

  const handleClose = () => {
    navigate('/soortgrootte');
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
      ...soortgrootteEntity,
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
          ...soortgrootteEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.soortgrootte.home.createOrEditLabel" data-cy="SoortgrootteCreateUpdateHeading">
            Create or edit a Soortgrootte
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="soortgrootte-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Codesoortgrootte"
                id="soortgrootte-codesoortgrootte"
                name="codesoortgrootte"
                data-cy="codesoortgrootte"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheidsoortgrootte"
                id="soortgrootte-datumbegingeldigheidsoortgrootte"
                name="datumbegingeldigheidsoortgrootte"
                data-cy="datumbegingeldigheidsoortgrootte"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidsoortgrootte"
                id="soortgrootte-datumeindegeldigheidsoortgrootte"
                name="datumeindegeldigheidsoortgrootte"
                data-cy="datumeindegeldigheidsoortgrootte"
                type="date"
              />
              <ValidatedField
                label="Naamsoortgrootte"
                id="soortgrootte-naamsoortgrootte"
                name="naamsoortgrootte"
                data-cy="naamsoortgrootte"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/soortgrootte" replace color="info">
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

export default SoortgrootteUpdate;
