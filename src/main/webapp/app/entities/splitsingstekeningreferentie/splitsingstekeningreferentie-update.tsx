import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISplitsingstekeningreferentie } from 'app/shared/model/splitsingstekeningreferentie.model';
import { getEntity, updateEntity, createEntity, reset } from './splitsingstekeningreferentie.reducer';

export const SplitsingstekeningreferentieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const splitsingstekeningreferentieEntity = useAppSelector(state => state.splitsingstekeningreferentie.entity);
  const loading = useAppSelector(state => state.splitsingstekeningreferentie.loading);
  const updating = useAppSelector(state => state.splitsingstekeningreferentie.updating);
  const updateSuccess = useAppSelector(state => state.splitsingstekeningreferentie.updateSuccess);

  const handleClose = () => {
    navigate('/splitsingstekeningreferentie');
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
      ...splitsingstekeningreferentieEntity,
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
          ...splitsingstekeningreferentieEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.splitsingstekeningreferentie.home.createOrEditLabel" data-cy="SplitsingstekeningreferentieCreateUpdateHeading">
            Create or edit a Splitsingstekeningreferentie
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
                <ValidatedField name="id" required readOnly id="splitsingstekeningreferentie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Bronorganisatie"
                id="splitsingstekeningreferentie-bronorganisatie"
                name="bronorganisatie"
                data-cy="bronorganisatie"
                type="text"
              />
              <ValidatedField
                label="Datumcreatie"
                id="splitsingstekeningreferentie-datumcreatie"
                name="datumcreatie"
                data-cy="datumcreatie"
                type="date"
              />
              <ValidatedField
                label="Identificatietekening"
                id="splitsingstekeningreferentie-identificatietekening"
                name="identificatietekening"
                data-cy="identificatietekening"
                type="text"
              />
              <ValidatedField label="Titel" id="splitsingstekeningreferentie-titel" name="titel" data-cy="titel" type="text" />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/splitsingstekeningreferentie"
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

export default SplitsingstekeningreferentieUpdate;
