import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAfvalbak } from 'app/shared/model/afvalbak.model';
import { getEntity, updateEntity, createEntity, reset } from './afvalbak.reducer';

export const AfvalbakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const afvalbakEntity = useAppSelector(state => state.afvalbak.entity);
  const loading = useAppSelector(state => state.afvalbak.loading);
  const updating = useAppSelector(state => state.afvalbak.updating);
  const updateSuccess = useAppSelector(state => state.afvalbak.updateSuccess);

  const handleClose = () => {
    navigate('/afvalbak');
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
      ...afvalbakEntity,
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
          ...afvalbakEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.afvalbak.home.createOrEditLabel" data-cy="AfvalbakCreateUpdateHeading">
            Create or edit a Afvalbak
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="afvalbak-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Type" id="afvalbak-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Typeplus" id="afvalbak-typeplus" name="typeplus" data-cy="typeplus" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/afvalbak" replace color="info">
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

export default AfvalbakUpdate;
