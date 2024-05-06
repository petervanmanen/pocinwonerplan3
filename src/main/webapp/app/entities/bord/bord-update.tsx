import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBord } from 'app/shared/model/bord.model';
import { getEntity, updateEntity, createEntity, reset } from './bord.reducer';

export const BordUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bordEntity = useAppSelector(state => state.bord.entity);
  const loading = useAppSelector(state => state.bord.loading);
  const updating = useAppSelector(state => state.bord.updating);
  const updateSuccess = useAppSelector(state => state.bord.updateSuccess);

  const handleClose = () => {
    navigate('/bord');
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
      ...bordEntity,
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
          ...bordEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.bord.home.createOrEditLabel" data-cy="BordCreateUpdateHeading">
            Create or edit a Bord
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="bord-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Breedte" id="bord-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField label="Diameter" id="bord-diameter" name="diameter" data-cy="diameter" type="text" />
              <ValidatedField label="Drager" id="bord-drager" name="drager" data-cy="drager" type="text" />
              <ValidatedField label="Hoogte" id="bord-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="bord-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField label="Lengte" id="bord-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField label="Leverancier" id="bord-leverancier" name="leverancier" data-cy="leverancier" type="text" />
              <ValidatedField label="Materiaal" id="bord-materiaal" name="materiaal" data-cy="materiaal" type="text" />
              <ValidatedField label="Vorm" id="bord-vorm" name="vorm" data-cy="vorm" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bord" replace color="info">
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

export default BordUpdate;
