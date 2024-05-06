import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWerkgelegenheid } from 'app/shared/model/werkgelegenheid.model';
import { getEntity, updateEntity, createEntity, reset } from './werkgelegenheid.reducer';

export const WerkgelegenheidUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const werkgelegenheidEntity = useAppSelector(state => state.werkgelegenheid.entity);
  const loading = useAppSelector(state => state.werkgelegenheid.loading);
  const updating = useAppSelector(state => state.werkgelegenheid.updating);
  const updateSuccess = useAppSelector(state => state.werkgelegenheid.updateSuccess);

  const handleClose = () => {
    navigate('/werkgelegenheid');
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
      ...werkgelegenheidEntity,
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
          ...werkgelegenheidEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.werkgelegenheid.home.createOrEditLabel" data-cy="WerkgelegenheidCreateUpdateHeading">
            Create or edit a Werkgelegenheid
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
                <ValidatedField name="id" required readOnly id="werkgelegenheid-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aantalfulltimemannen"
                id="werkgelegenheid-aantalfulltimemannen"
                name="aantalfulltimemannen"
                data-cy="aantalfulltimemannen"
                type="text"
              />
              <ValidatedField
                label="Aantalfulltimevrouwen"
                id="werkgelegenheid-aantalfulltimevrouwen"
                name="aantalfulltimevrouwen"
                data-cy="aantalfulltimevrouwen"
                type="text"
              />
              <ValidatedField
                label="Aantalparttimemannen"
                id="werkgelegenheid-aantalparttimemannen"
                name="aantalparttimemannen"
                data-cy="aantalparttimemannen"
                type="text"
              />
              <ValidatedField
                label="Aantalparttimevrouwen"
                id="werkgelegenheid-aantalparttimevrouwen"
                name="aantalparttimevrouwen"
                data-cy="aantalparttimevrouwen"
                type="text"
              />
              <ValidatedField
                label="Grootteklasse"
                id="werkgelegenheid-grootteklasse"
                name="grootteklasse"
                data-cy="grootteklasse"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/werkgelegenheid" replace color="info">
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

export default WerkgelegenheidUpdate;
