import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeveringsvorm } from 'app/shared/model/leveringsvorm.model';
import { getEntity, updateEntity, createEntity, reset } from './leveringsvorm.reducer';

export const LeveringsvormUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leveringsvormEntity = useAppSelector(state => state.leveringsvorm.entity);
  const loading = useAppSelector(state => state.leveringsvorm.loading);
  const updating = useAppSelector(state => state.leveringsvorm.updating);
  const updateSuccess = useAppSelector(state => state.leveringsvorm.updateSuccess);

  const handleClose = () => {
    navigate('/leveringsvorm');
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
      ...leveringsvormEntity,
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
          ...leveringsvormEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.leveringsvorm.home.createOrEditLabel" data-cy="LeveringsvormCreateUpdateHeading">
            Create or edit a Leveringsvorm
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
                <ValidatedField name="id" required readOnly id="leveringsvorm-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Leveringsvormcode"
                id="leveringsvorm-leveringsvormcode"
                name="leveringsvormcode"
                data-cy="leveringsvormcode"
                type="text"
              />
              <ValidatedField label="Naam" id="leveringsvorm-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Wet" id="leveringsvorm-wet" name="wet" data-cy="wet" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leveringsvorm" replace color="info">
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

export default LeveringsvormUpdate;
