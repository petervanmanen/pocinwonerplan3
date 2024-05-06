import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeidingelement } from 'app/shared/model/leidingelement.model';
import { getEntity, updateEntity, createEntity, reset } from './leidingelement.reducer';

export const LeidingelementUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leidingelementEntity = useAppSelector(state => state.leidingelement.entity);
  const loading = useAppSelector(state => state.leidingelement.loading);
  const updating = useAppSelector(state => state.leidingelement.updating);
  const updateSuccess = useAppSelector(state => state.leidingelement.updateSuccess);

  const handleClose = () => {
    navigate('/leidingelement');
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
      ...leidingelementEntity,
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
          ...leidingelementEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.leidingelement.home.createOrEditLabel" data-cy="LeidingelementCreateUpdateHeading">
            Create or edit a Leidingelement
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
                <ValidatedField name="id" required readOnly id="leidingelement-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Afwijkendedieptelegging"
                id="leidingelement-afwijkendedieptelegging"
                name="afwijkendedieptelegging"
                data-cy="afwijkendedieptelegging"
                type="text"
              />
              <ValidatedField label="Diepte" id="leidingelement-diepte" name="diepte" data-cy="diepte" type="text" />
              <ValidatedField
                label="Geonauwkeurigheidxy"
                id="leidingelement-geonauwkeurigheidxy"
                name="geonauwkeurigheidxy"
                data-cy="geonauwkeurigheidxy"
                type="text"
              />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="leidingelement-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField label="Leverancier" id="leidingelement-leverancier" name="leverancier" data-cy="leverancier" type="text" />
              <ValidatedField label="Themaimkl" id="leidingelement-themaimkl" name="themaimkl" data-cy="themaimkl" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leidingelement" replace color="info">
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

export default LeidingelementUpdate;
