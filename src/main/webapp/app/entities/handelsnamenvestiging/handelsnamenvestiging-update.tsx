import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHandelsnamenvestiging } from 'app/shared/model/handelsnamenvestiging.model';
import { getEntity, updateEntity, createEntity, reset } from './handelsnamenvestiging.reducer';

export const HandelsnamenvestigingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const handelsnamenvestigingEntity = useAppSelector(state => state.handelsnamenvestiging.entity);
  const loading = useAppSelector(state => state.handelsnamenvestiging.loading);
  const updating = useAppSelector(state => state.handelsnamenvestiging.updating);
  const updateSuccess = useAppSelector(state => state.handelsnamenvestiging.updateSuccess);

  const handleClose = () => {
    navigate('/handelsnamenvestiging');
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
      ...handelsnamenvestigingEntity,
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
          ...handelsnamenvestigingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.handelsnamenvestiging.home.createOrEditLabel" data-cy="HandelsnamenvestigingCreateUpdateHeading">
            Create or edit a Handelsnamenvestiging
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
                <ValidatedField name="id" required readOnly id="handelsnamenvestiging-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Handelsnaam"
                id="handelsnamenvestiging-handelsnaam"
                name="handelsnaam"
                data-cy="handelsnaam"
                type="text"
              />
              <ValidatedField
                label="Verkortenaam"
                id="handelsnamenvestiging-verkortenaam"
                name="verkortenaam"
                data-cy="verkortenaam"
                type="text"
              />
              <ValidatedField label="Volgorde" id="handelsnamenvestiging-volgorde" name="volgorde" data-cy="volgorde" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/handelsnamenvestiging" replace color="info">
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

export default HandelsnamenvestigingUpdate;
