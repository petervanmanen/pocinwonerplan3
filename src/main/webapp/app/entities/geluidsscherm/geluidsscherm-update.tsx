import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGeluidsscherm } from 'app/shared/model/geluidsscherm.model';
import { getEntity, updateEntity, createEntity, reset } from './geluidsscherm.reducer';

export const GeluidsschermUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const geluidsschermEntity = useAppSelector(state => state.geluidsscherm.entity);
  const loading = useAppSelector(state => state.geluidsscherm.loading);
  const updating = useAppSelector(state => state.geluidsscherm.updating);
  const updateSuccess = useAppSelector(state => state.geluidsscherm.updateSuccess);

  const handleClose = () => {
    navigate('/geluidsscherm');
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
      ...geluidsschermEntity,
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
          ...geluidsschermEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.geluidsscherm.home.createOrEditLabel" data-cy="GeluidsschermCreateUpdateHeading">
            Create or edit a Geluidsscherm
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
                <ValidatedField name="id" required readOnly id="geluidsscherm-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Aantaldeuren" id="geluidsscherm-aantaldeuren" name="aantaldeuren" data-cy="aantaldeuren" type="text" />
              <ValidatedField
                label="Aantalpanelen"
                id="geluidsscherm-aantalpanelen"
                name="aantalpanelen"
                data-cy="aantalpanelen"
                type="text"
              />
              <ValidatedField label="Type" id="geluidsscherm-type" name="type" data-cy="type" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/geluidsscherm" replace color="info">
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

export default GeluidsschermUpdate;
