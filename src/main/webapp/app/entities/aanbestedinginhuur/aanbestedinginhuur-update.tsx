import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAanbestedinginhuur } from 'app/shared/model/aanbestedinginhuur.model';
import { getEntity, updateEntity, createEntity, reset } from './aanbestedinginhuur.reducer';

export const AanbestedinginhuurUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aanbestedinginhuurEntity = useAppSelector(state => state.aanbestedinginhuur.entity);
  const loading = useAppSelector(state => state.aanbestedinginhuur.loading);
  const updating = useAppSelector(state => state.aanbestedinginhuur.updating);
  const updateSuccess = useAppSelector(state => state.aanbestedinginhuur.updateSuccess);

  const handleClose = () => {
    navigate('/aanbestedinginhuur');
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
      ...aanbestedinginhuurEntity,
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
          ...aanbestedinginhuurEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aanbestedinginhuur.home.createOrEditLabel" data-cy="AanbestedinginhuurCreateUpdateHeading">
            Create or edit a Aanbestedinginhuur
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
                <ValidatedField name="id" required readOnly id="aanbestedinginhuur-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aanvraaggesloten"
                id="aanbestedinginhuur-aanvraaggesloten"
                name="aanvraaggesloten"
                data-cy="aanvraaggesloten"
                type="text"
              />
              <ValidatedField
                label="Aanvraagnummer"
                id="aanbestedinginhuur-aanvraagnummer"
                name="aanvraagnummer"
                data-cy="aanvraagnummer"
                type="text"
              />
              <ValidatedField
                label="Datumcreatie"
                id="aanbestedinginhuur-datumcreatie"
                name="datumcreatie"
                data-cy="datumcreatie"
                type="text"
              />
              <ValidatedField
                label="Datumopeningkluis"
                id="aanbestedinginhuur-datumopeningkluis"
                name="datumopeningkluis"
                data-cy="datumopeningkluis"
                type="text"
              />
              <ValidatedField
                label="Datumsluiting"
                id="aanbestedinginhuur-datumsluiting"
                name="datumsluiting"
                data-cy="datumsluiting"
                type="text"
              />
              <ValidatedField
                label="Datumverzending"
                id="aanbestedinginhuur-datumverzending"
                name="datumverzending"
                data-cy="datumverzending"
                type="text"
              />
              <ValidatedField label="Fase" id="aanbestedinginhuur-fase" name="fase" data-cy="fase" type="text" />
              <ValidatedField
                label="Hoogstetarief"
                id="aanbestedinginhuur-hoogstetarief"
                name="hoogstetarief"
                data-cy="hoogstetarief"
                type="text"
              />
              <ValidatedField
                label="Laagstetarief"
                id="aanbestedinginhuur-laagstetarief"
                name="laagstetarief"
                data-cy="laagstetarief"
                type="text"
              />
              <ValidatedField
                label="Omschrijving"
                id="aanbestedinginhuur-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField label="Perceel" id="aanbestedinginhuur-perceel" name="perceel" data-cy="perceel" type="text" />
              <ValidatedField label="Procedure" id="aanbestedinginhuur-procedure" name="procedure" data-cy="procedure" type="text" />
              <ValidatedField
                label="Projectnaam"
                id="aanbestedinginhuur-projectnaam"
                name="projectnaam"
                data-cy="projectnaam"
                type="text"
              />
              <ValidatedField
                label="Projectreferentie"
                id="aanbestedinginhuur-projectreferentie"
                name="projectreferentie"
                data-cy="projectreferentie"
                type="text"
              />
              <ValidatedField label="Publicatie" id="aanbestedinginhuur-publicatie" name="publicatie" data-cy="publicatie" type="text" />
              <ValidatedField label="Referentie" id="aanbestedinginhuur-referentie" name="referentie" data-cy="referentie" type="text" />
              <ValidatedField label="Status" id="aanbestedinginhuur-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Titel" id="aanbestedinginhuur-titel" name="titel" data-cy="titel" type="text" />
              <ValidatedField label="Type" id="aanbestedinginhuur-type" name="type" data-cy="type" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aanbestedinginhuur" replace color="info">
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

export default AanbestedinginhuurUpdate;
