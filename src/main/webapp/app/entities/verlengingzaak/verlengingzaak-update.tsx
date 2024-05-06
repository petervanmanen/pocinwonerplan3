import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerlengingzaak } from 'app/shared/model/verlengingzaak.model';
import { getEntity, updateEntity, createEntity, reset } from './verlengingzaak.reducer';

export const VerlengingzaakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verlengingzaakEntity = useAppSelector(state => state.verlengingzaak.entity);
  const loading = useAppSelector(state => state.verlengingzaak.loading);
  const updating = useAppSelector(state => state.verlengingzaak.updating);
  const updateSuccess = useAppSelector(state => state.verlengingzaak.updateSuccess);

  const handleClose = () => {
    navigate('/verlengingzaak');
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
      ...verlengingzaakEntity,
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
          ...verlengingzaakEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verlengingzaak.home.createOrEditLabel" data-cy="VerlengingzaakCreateUpdateHeading">
            Create or edit a Verlengingzaak
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
                <ValidatedField name="id" required readOnly id="verlengingzaak-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Duurverlenging"
                id="verlengingzaak-duurverlenging"
                name="duurverlenging"
                data-cy="duurverlenging"
                type="text"
              />
              <ValidatedField
                label="Redenverlenging"
                id="verlengingzaak-redenverlenging"
                name="redenverlenging"
                data-cy="redenverlenging"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verlengingzaak" replace color="info">
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

export default VerlengingzaakUpdate;
