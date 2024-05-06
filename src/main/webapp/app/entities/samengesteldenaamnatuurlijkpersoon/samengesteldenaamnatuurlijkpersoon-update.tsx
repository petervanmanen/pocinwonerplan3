import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISamengesteldenaamnatuurlijkpersoon } from 'app/shared/model/samengesteldenaamnatuurlijkpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './samengesteldenaamnatuurlijkpersoon.reducer';

export const SamengesteldenaamnatuurlijkpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const samengesteldenaamnatuurlijkpersoonEntity = useAppSelector(state => state.samengesteldenaamnatuurlijkpersoon.entity);
  const loading = useAppSelector(state => state.samengesteldenaamnatuurlijkpersoon.loading);
  const updating = useAppSelector(state => state.samengesteldenaamnatuurlijkpersoon.updating);
  const updateSuccess = useAppSelector(state => state.samengesteldenaamnatuurlijkpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/samengesteldenaamnatuurlijkpersoon');
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
      ...samengesteldenaamnatuurlijkpersoonEntity,
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
          ...samengesteldenaamnatuurlijkpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.samengesteldenaamnatuurlijkpersoon.home.createOrEditLabel"
            data-cy="SamengesteldenaamnatuurlijkpersoonCreateUpdateHeading"
          >
            Create or edit a Samengesteldenaamnatuurlijkpersoon
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
                  id="samengesteldenaamnatuurlijkpersoon-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Adellijketitel"
                id="samengesteldenaamnatuurlijkpersoon-adellijketitel"
                name="adellijketitel"
                data-cy="adellijketitel"
                type="text"
              />
              <ValidatedField
                label="Geslachtsnaamstam"
                id="samengesteldenaamnatuurlijkpersoon-geslachtsnaamstam"
                name="geslachtsnaamstam"
                data-cy="geslachtsnaamstam"
                type="text"
              />
              <ValidatedField
                label="Namenreeks"
                id="samengesteldenaamnatuurlijkpersoon-namenreeks"
                name="namenreeks"
                data-cy="namenreeks"
                type="text"
              />
              <ValidatedField
                label="Predicaat"
                id="samengesteldenaamnatuurlijkpersoon-predicaat"
                name="predicaat"
                data-cy="predicaat"
                type="text"
              />
              <ValidatedField
                label="Scheidingsteken"
                id="samengesteldenaamnatuurlijkpersoon-scheidingsteken"
                name="scheidingsteken"
                data-cy="scheidingsteken"
                type="text"
              />
              <ValidatedField
                label="Voornamen"
                id="samengesteldenaamnatuurlijkpersoon-voornamen"
                name="voornamen"
                data-cy="voornamen"
                type="text"
              />
              <ValidatedField
                label="Voorvoegsel"
                id="samengesteldenaamnatuurlijkpersoon-voorvoegsel"
                name="voorvoegsel"
                data-cy="voorvoegsel"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/samengesteldenaamnatuurlijkpersoon"
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

export default SamengesteldenaamnatuurlijkpersoonUpdate;
