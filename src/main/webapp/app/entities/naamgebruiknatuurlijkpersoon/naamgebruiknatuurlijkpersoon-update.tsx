import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INaamgebruiknatuurlijkpersoon } from 'app/shared/model/naamgebruiknatuurlijkpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './naamgebruiknatuurlijkpersoon.reducer';

export const NaamgebruiknatuurlijkpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const naamgebruiknatuurlijkpersoonEntity = useAppSelector(state => state.naamgebruiknatuurlijkpersoon.entity);
  const loading = useAppSelector(state => state.naamgebruiknatuurlijkpersoon.loading);
  const updating = useAppSelector(state => state.naamgebruiknatuurlijkpersoon.updating);
  const updateSuccess = useAppSelector(state => state.naamgebruiknatuurlijkpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/naamgebruiknatuurlijkpersoon');
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
      ...naamgebruiknatuurlijkpersoonEntity,
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
          ...naamgebruiknatuurlijkpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.naamgebruiknatuurlijkpersoon.home.createOrEditLabel" data-cy="NaamgebruiknatuurlijkpersoonCreateUpdateHeading">
            Create or edit a Naamgebruiknatuurlijkpersoon
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
                <ValidatedField name="id" required readOnly id="naamgebruiknatuurlijkpersoon-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aanhefaanschrijving"
                id="naamgebruiknatuurlijkpersoon-aanhefaanschrijving"
                name="aanhefaanschrijving"
                data-cy="aanhefaanschrijving"
                type="text"
              />
              <ValidatedField
                label="Adellijketitelnaamgebruik"
                id="naamgebruiknatuurlijkpersoon-adellijketitelnaamgebruik"
                name="adellijketitelnaamgebruik"
                data-cy="adellijketitelnaamgebruik"
                type="text"
              />
              <ValidatedField
                label="Geslachtsnaamstamnaamgebruik"
                id="naamgebruiknatuurlijkpersoon-geslachtsnaamstamnaamgebruik"
                name="geslachtsnaamstamnaamgebruik"
                data-cy="geslachtsnaamstamnaamgebruik"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/naamgebruiknatuurlijkpersoon"
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

export default NaamgebruiknatuurlijkpersoonUpdate;
