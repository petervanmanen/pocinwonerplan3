import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBouwactiviteit } from 'app/shared/model/bouwactiviteit.model';
import { getEntity, updateEntity, createEntity, reset } from './bouwactiviteit.reducer';

export const BouwactiviteitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bouwactiviteitEntity = useAppSelector(state => state.bouwactiviteit.entity);
  const loading = useAppSelector(state => state.bouwactiviteit.loading);
  const updating = useAppSelector(state => state.bouwactiviteit.updating);
  const updateSuccess = useAppSelector(state => state.bouwactiviteit.updateSuccess);

  const handleClose = () => {
    navigate('/bouwactiviteit');
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
      ...bouwactiviteitEntity,
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
          ...bouwactiviteitEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.bouwactiviteit.home.createOrEditLabel" data-cy="BouwactiviteitCreateUpdateHeading">
            Create or edit a Bouwactiviteit
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
                <ValidatedField name="id" required readOnly id="bouwactiviteit-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Bouwjaarklasse"
                id="bouwactiviteit-bouwjaarklasse"
                name="bouwjaarklasse"
                data-cy="bouwjaarklasse"
                type="text"
              />
              <ValidatedField label="Bouwjaartot" id="bouwactiviteit-bouwjaartot" name="bouwjaartot" data-cy="bouwjaartot" type="text" />
              <ValidatedField label="Bouwjaarvan" id="bouwactiviteit-bouwjaarvan" name="bouwjaarvan" data-cy="bouwjaarvan" type="text" />
              <ValidatedField
                label="Indicatie"
                id="bouwactiviteit-indicatie"
                name="indicatie"
                data-cy="indicatie"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField
                label="Omschrijving"
                id="bouwactiviteit-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bouwactiviteit" replace color="info">
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

export default BouwactiviteitUpdate;
