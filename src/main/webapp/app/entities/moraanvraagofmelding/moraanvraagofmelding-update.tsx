import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMoraanvraagofmelding } from 'app/shared/model/moraanvraagofmelding.model';
import { getEntity, updateEntity, createEntity, reset } from './moraanvraagofmelding.reducer';

export const MoraanvraagofmeldingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const moraanvraagofmeldingEntity = useAppSelector(state => state.moraanvraagofmelding.entity);
  const loading = useAppSelector(state => state.moraanvraagofmelding.loading);
  const updating = useAppSelector(state => state.moraanvraagofmelding.updating);
  const updateSuccess = useAppSelector(state => state.moraanvraagofmelding.updateSuccess);

  const handleClose = () => {
    navigate('/moraanvraagofmelding');
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
      ...moraanvraagofmeldingEntity,
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
          ...moraanvraagofmeldingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.moraanvraagofmelding.home.createOrEditLabel" data-cy="MoraanvraagofmeldingCreateUpdateHeading">
            Create or edit a Moraanvraagofmelding
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
                <ValidatedField name="id" required readOnly id="moraanvraagofmelding-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Crow" id="moraanvraagofmelding-crow" name="crow" data-cy="crow" type="text" />
              <ValidatedField label="Locatie" id="moraanvraagofmelding-locatie" name="locatie" data-cy="locatie" type="text" />
              <ValidatedField
                label="Locatieomschrijving"
                id="moraanvraagofmelding-locatieomschrijving"
                name="locatieomschrijving"
                data-cy="locatieomschrijving"
                type="text"
              />
              <ValidatedField
                label="Meldingomschrijving"
                id="moraanvraagofmelding-meldingomschrijving"
                name="meldingomschrijving"
                data-cy="meldingomschrijving"
                type="text"
              />
              <ValidatedField
                label="Meldingtekst"
                id="moraanvraagofmelding-meldingtekst"
                name="meldingtekst"
                data-cy="meldingtekst"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/moraanvraagofmelding" replace color="info">
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

export default MoraanvraagofmeldingUpdate;
