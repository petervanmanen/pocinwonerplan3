import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBouwwerk } from 'app/shared/model/bouwwerk.model';
import { getEntity, updateEntity, createEntity, reset } from './bouwwerk.reducer';

export const BouwwerkUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bouwwerkEntity = useAppSelector(state => state.bouwwerk.entity);
  const loading = useAppSelector(state => state.bouwwerk.loading);
  const updating = useAppSelector(state => state.bouwwerk.updating);
  const updateSuccess = useAppSelector(state => state.bouwwerk.updateSuccess);

  const handleClose = () => {
    navigate('/bouwwerk');
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
      ...bouwwerkEntity,
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
          ...bouwwerkEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.bouwwerk.home.createOrEditLabel" data-cy="BouwwerkCreateUpdateHeading">
            Create or edit a Bouwwerk
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="bouwwerk-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aanleghoogte" id="bouwwerk-aanleghoogte" name="aanleghoogte" data-cy="aanleghoogte" type="text" />
              <ValidatedField
                label="Bouwwerkmateriaal"
                id="bouwwerk-bouwwerkmateriaal"
                name="bouwwerkmateriaal"
                data-cy="bouwwerkmateriaal"
                type="text"
              />
              <ValidatedField label="Breedte" id="bouwwerk-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField label="Fabrikant" id="bouwwerk-fabrikant" name="fabrikant" data-cy="fabrikant" type="text" />
              <ValidatedField label="Hoogte" id="bouwwerk-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="bouwwerk-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField label="Lengte" id="bouwwerk-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField label="Leverancier" id="bouwwerk-leverancier" name="leverancier" data-cy="leverancier" type="text" />
              <ValidatedField label="Oppervlakte" id="bouwwerk-oppervlakte" name="oppervlakte" data-cy="oppervlakte" type="text" />
              <ValidatedField label="Typefundering" id="bouwwerk-typefundering" name="typefundering" data-cy="typefundering" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bouwwerk" replace color="info">
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

export default BouwwerkUpdate;
