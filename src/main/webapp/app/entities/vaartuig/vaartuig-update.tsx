import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVaartuig } from 'app/shared/model/vaartuig.model';
import { getEntity, updateEntity, createEntity, reset } from './vaartuig.reducer';

export const VaartuigUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vaartuigEntity = useAppSelector(state => state.vaartuig.entity);
  const loading = useAppSelector(state => state.vaartuig.loading);
  const updating = useAppSelector(state => state.vaartuig.updating);
  const updateSuccess = useAppSelector(state => state.vaartuig.updateSuccess);

  const handleClose = () => {
    navigate('/vaartuig');
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
      ...vaartuigEntity,
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
          ...vaartuigEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vaartuig.home.createOrEditLabel" data-cy="VaartuigCreateUpdateHeading">
            Create or edit a Vaartuig
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vaartuig-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Breedte" id="vaartuig-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField label="Hoogte" id="vaartuig-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField
                label="Kleur"
                id="vaartuig-kleur"
                name="kleur"
                data-cy="kleur"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Lengte" id="vaartuig-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField label="Naamvaartuig" id="vaartuig-naamvaartuig" name="naamvaartuig" data-cy="naamvaartuig" type="text" />
              <ValidatedField
                label="Registratienummer"
                id="vaartuig-registratienummer"
                name="registratienummer"
                data-cy="registratienummer"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vaartuig" replace color="info">
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

export default VaartuigUpdate;
