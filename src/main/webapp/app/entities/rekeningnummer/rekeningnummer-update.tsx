import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRekeningnummer } from 'app/shared/model/rekeningnummer.model';
import { getEntity, updateEntity, createEntity, reset } from './rekeningnummer.reducer';

export const RekeningnummerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rekeningnummerEntity = useAppSelector(state => state.rekeningnummer.entity);
  const loading = useAppSelector(state => state.rekeningnummer.loading);
  const updating = useAppSelector(state => state.rekeningnummer.updating);
  const updateSuccess = useAppSelector(state => state.rekeningnummer.updateSuccess);

  const handleClose = () => {
    navigate('/rekeningnummer');
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
      ...rekeningnummerEntity,
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
          ...rekeningnummerEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.rekeningnummer.home.createOrEditLabel" data-cy="RekeningnummerCreateUpdateHeading">
            Create or edit a Rekeningnummer
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
                <ValidatedField name="id" required readOnly id="rekeningnummer-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Bic" id="rekeningnummer-bic" name="bic" data-cy="bic" type="text" />
              <ValidatedField label="Iban" id="rekeningnummer-iban" name="iban" data-cy="iban" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rekeningnummer" replace color="info">
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

export default RekeningnummerUpdate;
