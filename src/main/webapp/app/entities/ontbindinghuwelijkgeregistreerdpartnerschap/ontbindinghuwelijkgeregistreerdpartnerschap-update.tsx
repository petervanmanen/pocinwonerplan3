import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOntbindinghuwelijkgeregistreerdpartnerschap } from 'app/shared/model/ontbindinghuwelijkgeregistreerdpartnerschap.model';
import { getEntity, updateEntity, createEntity, reset } from './ontbindinghuwelijkgeregistreerdpartnerschap.reducer';

export const OntbindinghuwelijkgeregistreerdpartnerschapUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ontbindinghuwelijkgeregistreerdpartnerschapEntity = useAppSelector(
    state => state.ontbindinghuwelijkgeregistreerdpartnerschap.entity,
  );
  const loading = useAppSelector(state => state.ontbindinghuwelijkgeregistreerdpartnerschap.loading);
  const updating = useAppSelector(state => state.ontbindinghuwelijkgeregistreerdpartnerschap.updating);
  const updateSuccess = useAppSelector(state => state.ontbindinghuwelijkgeregistreerdpartnerschap.updateSuccess);

  const handleClose = () => {
    navigate('/ontbindinghuwelijkgeregistreerdpartnerschap');
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
      ...ontbindinghuwelijkgeregistreerdpartnerschapEntity,
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
          ...ontbindinghuwelijkgeregistreerdpartnerschapEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.ontbindinghuwelijkgeregistreerdpartnerschap.home.createOrEditLabel"
            data-cy="OntbindinghuwelijkgeregistreerdpartnerschapCreateUpdateHeading"
          >
            Create or edit a Ontbindinghuwelijkgeregistreerdpartnerschap
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
                  id="ontbindinghuwelijkgeregistreerdpartnerschap-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Buitenlandseplaatseinde"
                id="ontbindinghuwelijkgeregistreerdpartnerschap-buitenlandseplaatseinde"
                name="buitenlandseplaatseinde"
                data-cy="buitenlandseplaatseinde"
                type="text"
              />
              <ValidatedField
                label="Buitenlandseregioeinde"
                id="ontbindinghuwelijkgeregistreerdpartnerschap-buitenlandseregioeinde"
                name="buitenlandseregioeinde"
                data-cy="buitenlandseregioeinde"
                type="text"
              />
              <ValidatedField
                label="Datumeinde"
                id="ontbindinghuwelijkgeregistreerdpartnerschap-datumeinde"
                name="datumeinde"
                data-cy="datumeinde"
                type="text"
              />
              <ValidatedField
                label="Gemeenteeinde"
                id="ontbindinghuwelijkgeregistreerdpartnerschap-gemeenteeinde"
                name="gemeenteeinde"
                data-cy="gemeenteeinde"
                type="text"
              />
              <ValidatedField
                label="Landofgebiedeinde"
                id="ontbindinghuwelijkgeregistreerdpartnerschap-landofgebiedeinde"
                name="landofgebiedeinde"
                data-cy="landofgebiedeinde"
                type="text"
              />
              <ValidatedField
                label="Omschrijvinglocatieeinde"
                id="ontbindinghuwelijkgeregistreerdpartnerschap-omschrijvinglocatieeinde"
                name="omschrijvinglocatieeinde"
                data-cy="omschrijvinglocatieeinde"
                type="text"
              />
              <ValidatedField
                label="Redeneinde"
                id="ontbindinghuwelijkgeregistreerdpartnerschap-redeneinde"
                name="redeneinde"
                data-cy="redeneinde"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/ontbindinghuwelijkgeregistreerdpartnerschap"
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

export default OntbindinghuwelijkgeregistreerdpartnerschapUpdate;
