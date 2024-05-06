import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKadastraleonroerendezaakaantekening } from 'app/shared/model/kadastraleonroerendezaakaantekening.model';
import { getEntity, updateEntity, createEntity, reset } from './kadastraleonroerendezaakaantekening.reducer';

export const KadastraleonroerendezaakaantekeningUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kadastraleonroerendezaakaantekeningEntity = useAppSelector(state => state.kadastraleonroerendezaakaantekening.entity);
  const loading = useAppSelector(state => state.kadastraleonroerendezaakaantekening.loading);
  const updating = useAppSelector(state => state.kadastraleonroerendezaakaantekening.updating);
  const updateSuccess = useAppSelector(state => state.kadastraleonroerendezaakaantekening.updateSuccess);

  const handleClose = () => {
    navigate('/kadastraleonroerendezaakaantekening');
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
      ...kadastraleonroerendezaakaantekeningEntity,
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
          ...kadastraleonroerendezaakaantekeningEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.kadastraleonroerendezaakaantekening.home.createOrEditLabel"
            data-cy="KadastraleonroerendezaakaantekeningCreateUpdateHeading"
          >
            Create or edit a Kadastraleonroerendezaakaantekening
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
                  id="kadastraleonroerendezaakaantekening-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Aardaantekeningkadastraalobject"
                id="kadastraleonroerendezaakaantekening-aardaantekeningkadastraalobject"
                name="aardaantekeningkadastraalobject"
                data-cy="aardaantekeningkadastraalobject"
                type="text"
              />
              <ValidatedField
                label="Beschrijvingaantekeningkadastraalobject"
                id="kadastraleonroerendezaakaantekening-beschrijvingaantekeningkadastraalobject"
                name="beschrijvingaantekeningkadastraalobject"
                data-cy="beschrijvingaantekeningkadastraalobject"
                type="text"
              />
              <ValidatedField
                label="Datumbeginaantekeningkadastraalobject"
                id="kadastraleonroerendezaakaantekening-datumbeginaantekeningkadastraalobject"
                name="datumbeginaantekeningkadastraalobject"
                data-cy="datumbeginaantekeningkadastraalobject"
                type="date"
              />
              <ValidatedField
                label="Datumeindeaantekeningkadastraalobject"
                id="kadastraleonroerendezaakaantekening-datumeindeaantekeningkadastraalobject"
                name="datumeindeaantekeningkadastraalobject"
                data-cy="datumeindeaantekeningkadastraalobject"
                type="date"
              />
              <ValidatedField
                label="Kadasteridentificatieaantekening"
                id="kadastraleonroerendezaakaantekening-kadasteridentificatieaantekening"
                name="kadasteridentificatieaantekening"
                data-cy="kadasteridentificatieaantekening"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/kadastraleonroerendezaakaantekening"
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

export default KadastraleonroerendezaakaantekeningUpdate;
