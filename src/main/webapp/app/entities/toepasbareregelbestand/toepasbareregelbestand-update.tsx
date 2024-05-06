import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IToepasbareregelbestand } from 'app/shared/model/toepasbareregelbestand.model';
import { getEntity, updateEntity, createEntity, reset } from './toepasbareregelbestand.reducer';

export const ToepasbareregelbestandUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const toepasbareregelbestandEntity = useAppSelector(state => state.toepasbareregelbestand.entity);
  const loading = useAppSelector(state => state.toepasbareregelbestand.loading);
  const updating = useAppSelector(state => state.toepasbareregelbestand.updating);
  const updateSuccess = useAppSelector(state => state.toepasbareregelbestand.updateSuccess);

  const handleClose = () => {
    navigate('/toepasbareregelbestand');
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
      ...toepasbareregelbestandEntity,
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
          ...toepasbareregelbestandEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.toepasbareregelbestand.home.createOrEditLabel" data-cy="ToepasbareregelbestandCreateUpdateHeading">
            Create or edit a Toepasbareregelbestand
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
                <ValidatedField name="id" required readOnly id="toepasbareregelbestand-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumeindegeldigheid"
                id="toepasbareregelbestand-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField
                label="Datumstart"
                id="toepasbareregelbestand-datumstart"
                name="datumstart"
                data-cy="datumstart"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/toepasbareregelbestand" replace color="info">
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

export default ToepasbareregelbestandUpdate;
