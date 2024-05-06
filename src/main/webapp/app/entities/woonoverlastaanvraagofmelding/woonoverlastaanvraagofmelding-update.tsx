import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWoonoverlastaanvraagofmelding } from 'app/shared/model/woonoverlastaanvraagofmelding.model';
import { getEntity, updateEntity, createEntity, reset } from './woonoverlastaanvraagofmelding.reducer';

export const WoonoverlastaanvraagofmeldingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const woonoverlastaanvraagofmeldingEntity = useAppSelector(state => state.woonoverlastaanvraagofmelding.entity);
  const loading = useAppSelector(state => state.woonoverlastaanvraagofmelding.loading);
  const updating = useAppSelector(state => state.woonoverlastaanvraagofmelding.updating);
  const updateSuccess = useAppSelector(state => state.woonoverlastaanvraagofmelding.updateSuccess);

  const handleClose = () => {
    navigate('/woonoverlastaanvraagofmelding');
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
      ...woonoverlastaanvraagofmeldingEntity,
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
          ...woonoverlastaanvraagofmeldingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.woonoverlastaanvraagofmelding.home.createOrEditLabel" data-cy="WoonoverlastaanvraagofmeldingCreateUpdateHeading">
            Create or edit a Woonoverlastaanvraagofmelding
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="woonoverlastaanvraagofmelding-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label="Locatie" id="woonoverlastaanvraagofmelding-locatie" name="locatie" data-cy="locatie" type="text" />
              <ValidatedField
                label="Locatieomschrijving"
                id="woonoverlastaanvraagofmelding-locatieomschrijving"
                name="locatieomschrijving"
                data-cy="locatieomschrijving"
                type="text"
              />
              <ValidatedField
                label="Meldingomschrijving"
                id="woonoverlastaanvraagofmelding-meldingomschrijving"
                name="meldingomschrijving"
                data-cy="meldingomschrijving"
                type="text"
              />
              <ValidatedField
                label="Meldingtekst"
                id="woonoverlastaanvraagofmelding-meldingtekst"
                name="meldingtekst"
                data-cy="meldingtekst"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/woonoverlastaanvraagofmelding"
                replace
                color="info"
              >
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

export default WoonoverlastaanvraagofmeldingUpdate;
