import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJuridischeregel } from 'app/shared/model/juridischeregel.model';
import { getEntity, updateEntity, createEntity, reset } from './juridischeregel.reducer';

export const JuridischeregelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const juridischeregelEntity = useAppSelector(state => state.juridischeregel.entity);
  const loading = useAppSelector(state => state.juridischeregel.loading);
  const updating = useAppSelector(state => state.juridischeregel.updating);
  const updateSuccess = useAppSelector(state => state.juridischeregel.updateSuccess);

  const handleClose = () => {
    navigate('/juridischeregel');
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
      ...juridischeregelEntity,
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
          ...juridischeregelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.juridischeregel.home.createOrEditLabel" data-cy="JuridischeregelCreateUpdateHeading">
            Create or edit a Juridischeregel
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
                <ValidatedField name="id" required readOnly id="juridischeregel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datumbekend" id="juridischeregel-datumbekend" name="datumbekend" data-cy="datumbekend" type="date" />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="juridischeregel-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField
                label="Datuminwerking"
                id="juridischeregel-datuminwerking"
                name="datuminwerking"
                data-cy="datuminwerking"
                type="date"
              />
              <ValidatedField label="Datumstart" id="juridischeregel-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField
                label="Omschrijving"
                id="juridischeregel-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField label="Regeltekst" id="juridischeregel-regeltekst" name="regeltekst" data-cy="regeltekst" type="text" />
              <ValidatedField label="Thema" id="juridischeregel-thema" name="thema" data-cy="thema" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/juridischeregel" replace color="info">
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

export default JuridischeregelUpdate;
