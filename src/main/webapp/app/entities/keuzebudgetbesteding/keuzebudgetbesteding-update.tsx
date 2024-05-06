import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKeuzebudgetbesteding } from 'app/shared/model/keuzebudgetbesteding.model';
import { getEntity, updateEntity, createEntity, reset } from './keuzebudgetbesteding.reducer';

export const KeuzebudgetbestedingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const keuzebudgetbestedingEntity = useAppSelector(state => state.keuzebudgetbesteding.entity);
  const loading = useAppSelector(state => state.keuzebudgetbesteding.loading);
  const updating = useAppSelector(state => state.keuzebudgetbesteding.updating);
  const updateSuccess = useAppSelector(state => state.keuzebudgetbesteding.updateSuccess);

  const handleClose = () => {
    navigate('/keuzebudgetbesteding');
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
    if (values.bedrag !== undefined && typeof values.bedrag !== 'number') {
      values.bedrag = Number(values.bedrag);
    }

    const entity = {
      ...keuzebudgetbestedingEntity,
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
          ...keuzebudgetbestedingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.keuzebudgetbesteding.home.createOrEditLabel" data-cy="KeuzebudgetbestedingCreateUpdateHeading">
            Create or edit a Keuzebudgetbesteding
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
                <ValidatedField name="id" required readOnly id="keuzebudgetbesteding-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Bedrag" id="keuzebudgetbesteding-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField label="Datum" id="keuzebudgetbesteding-datum" name="datum" data-cy="datum" type="date" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/keuzebudgetbesteding" replace color="info">
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

export default KeuzebudgetbestedingUpdate;
