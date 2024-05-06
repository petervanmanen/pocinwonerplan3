import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKoopsomkadastraleonroerendezaak } from 'app/shared/model/koopsomkadastraleonroerendezaak.model';
import { getEntity, updateEntity, createEntity, reset } from './koopsomkadastraleonroerendezaak.reducer';

export const KoopsomkadastraleonroerendezaakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const koopsomkadastraleonroerendezaakEntity = useAppSelector(state => state.koopsomkadastraleonroerendezaak.entity);
  const loading = useAppSelector(state => state.koopsomkadastraleonroerendezaak.loading);
  const updating = useAppSelector(state => state.koopsomkadastraleonroerendezaak.updating);
  const updateSuccess = useAppSelector(state => state.koopsomkadastraleonroerendezaak.updateSuccess);

  const handleClose = () => {
    navigate('/koopsomkadastraleonroerendezaak');
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
      ...koopsomkadastraleonroerendezaakEntity,
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
          ...koopsomkadastraleonroerendezaakEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.koopsomkadastraleonroerendezaak.home.createOrEditLabel"
            data-cy="KoopsomkadastraleonroerendezaakCreateUpdateHeading"
          >
            Create or edit a Koopsomkadastraleonroerendezaak
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
                  id="koopsomkadastraleonroerendezaak-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Datumtransactie"
                id="koopsomkadastraleonroerendezaak-datumtransactie"
                name="datumtransactie"
                data-cy="datumtransactie"
                type="text"
              />
              <ValidatedField label="Koopsom" id="koopsomkadastraleonroerendezaak-koopsom" name="koopsom" data-cy="koopsom" type="text" />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/koopsomkadastraleonroerendezaak"
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

export default KoopsomkadastraleonroerendezaakUpdate;
