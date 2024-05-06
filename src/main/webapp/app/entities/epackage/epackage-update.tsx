import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEpackage } from 'app/shared/model/epackage.model';
import { getEntity, updateEntity, createEntity, reset } from './epackage.reducer';

export const EpackageUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const epackageEntity = useAppSelector(state => state.epackage.entity);
  const loading = useAppSelector(state => state.epackage.loading);
  const updating = useAppSelector(state => state.epackage.updating);
  const updateSuccess = useAppSelector(state => state.epackage.updateSuccess);

  const handleClose = () => {
    navigate('/epackage');
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
      ...epackageEntity,
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
          ...epackageEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.epackage.home.createOrEditLabel" data-cy="EpackageCreateUpdateHeading">
            Create or edit a Epackage
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="epackage-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="epackage-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Proces" id="epackage-proces" name="proces" data-cy="proces" type="text" />
              <ValidatedField label="Project" id="epackage-project" name="project" data-cy="project" type="text" />
              <ValidatedField label="Status" id="epackage-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Toelichting" id="epackage-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/epackage" replace color="info">
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

export default EpackageUpdate;
