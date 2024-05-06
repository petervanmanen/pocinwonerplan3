import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRedenverliesnationaliteit } from 'app/shared/model/redenverliesnationaliteit.model';
import { getEntity, updateEntity, createEntity, reset } from './redenverliesnationaliteit.reducer';

export const RedenverliesnationaliteitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const redenverliesnationaliteitEntity = useAppSelector(state => state.redenverliesnationaliteit.entity);
  const loading = useAppSelector(state => state.redenverliesnationaliteit.loading);
  const updating = useAppSelector(state => state.redenverliesnationaliteit.updating);
  const updateSuccess = useAppSelector(state => state.redenverliesnationaliteit.updateSuccess);

  const handleClose = () => {
    navigate('/redenverliesnationaliteit');
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
      ...redenverliesnationaliteitEntity,
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
          ...redenverliesnationaliteitEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.redenverliesnationaliteit.home.createOrEditLabel" data-cy="RedenverliesnationaliteitCreateUpdateHeading">
            Create or edit a Redenverliesnationaliteit
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
                <ValidatedField name="id" required readOnly id="redenverliesnationaliteit-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumaanvanggeldigheidverlies"
                id="redenverliesnationaliteit-datumaanvanggeldigheidverlies"
                name="datumaanvanggeldigheidverlies"
                data-cy="datumaanvanggeldigheidverlies"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidverlies"
                id="redenverliesnationaliteit-datumeindegeldigheidverlies"
                name="datumeindegeldigheidverlies"
                data-cy="datumeindegeldigheidverlies"
                type="date"
              />
              <ValidatedField
                label="Omschrijvingverlies"
                id="redenverliesnationaliteit-omschrijvingverlies"
                name="omschrijvingverlies"
                data-cy="omschrijvingverlies"
                type="text"
              />
              <ValidatedField
                label="Redennummerverlies"
                id="redenverliesnationaliteit-redennummerverlies"
                name="redennummerverlies"
                data-cy="redennummerverlies"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/redenverliesnationaliteit" replace color="info">
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

export default RedenverliesnationaliteitUpdate;
