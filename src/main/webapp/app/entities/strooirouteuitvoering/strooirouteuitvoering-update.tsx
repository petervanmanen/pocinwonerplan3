import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStrooirouteuitvoering } from 'app/shared/model/strooirouteuitvoering.model';
import { getEntity, updateEntity, createEntity, reset } from './strooirouteuitvoering.reducer';

export const StrooirouteuitvoeringUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const strooirouteuitvoeringEntity = useAppSelector(state => state.strooirouteuitvoering.entity);
  const loading = useAppSelector(state => state.strooirouteuitvoering.loading);
  const updating = useAppSelector(state => state.strooirouteuitvoering.updating);
  const updateSuccess = useAppSelector(state => state.strooirouteuitvoering.updateSuccess);

  const handleClose = () => {
    navigate('/strooirouteuitvoering');
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
      ...strooirouteuitvoeringEntity,
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
          ...strooirouteuitvoeringEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.strooirouteuitvoering.home.createOrEditLabel" data-cy="StrooirouteuitvoeringCreateUpdateHeading">
            Create or edit a Strooirouteuitvoering
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
                <ValidatedField name="id" required readOnly id="strooirouteuitvoering-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Geplandeinde"
                id="strooirouteuitvoering-geplandeinde"
                name="geplandeinde"
                data-cy="geplandeinde"
                type="text"
              />
              <ValidatedField
                label="Geplandstart"
                id="strooirouteuitvoering-geplandstart"
                name="geplandstart"
                data-cy="geplandstart"
                type="text"
              />
              <ValidatedField label="Eroute" id="strooirouteuitvoering-eroute" name="eroute" data-cy="eroute" type="text" />
              <ValidatedField
                label="Werkelijkeinde"
                id="strooirouteuitvoering-werkelijkeinde"
                name="werkelijkeinde"
                data-cy="werkelijkeinde"
                type="text"
              />
              <ValidatedField
                label="Werkelijkestart"
                id="strooirouteuitvoering-werkelijkestart"
                name="werkelijkestart"
                data-cy="werkelijkestart"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/strooirouteuitvoering" replace color="info">
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

export default StrooirouteuitvoeringUpdate;
