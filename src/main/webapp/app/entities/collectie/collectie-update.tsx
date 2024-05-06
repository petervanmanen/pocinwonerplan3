import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMuseumobject } from 'app/shared/model/museumobject.model';
import { getEntities as getMuseumobjects } from 'app/entities/museumobject/museumobject.reducer';
import { ICollectie } from 'app/shared/model/collectie.model';
import { getEntity, updateEntity, createEntity, reset } from './collectie.reducer';

export const CollectieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const museumobjects = useAppSelector(state => state.museumobject.entities);
  const collectieEntity = useAppSelector(state => state.collectie.entity);
  const loading = useAppSelector(state => state.collectie.loading);
  const updating = useAppSelector(state => state.collectie.updating);
  const updateSuccess = useAppSelector(state => state.collectie.updateSuccess);

  const handleClose = () => {
    navigate('/collectie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMuseumobjects({}));
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
      ...collectieEntity,
      ...values,
      bevatMuseumobjects: mapIdList(values.bevatMuseumobjects),
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
          ...collectieEntity,
          bevatMuseumobjects: collectieEntity?.bevatMuseumobjects?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.collectie.home.createOrEditLabel" data-cy="CollectieCreateUpdateHeading">
            Create or edit a Collectie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="collectie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="collectie-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="collectie-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Bevat Museumobject"
                id="collectie-bevatMuseumobject"
                data-cy="bevatMuseumobject"
                type="select"
                multiple
                name="bevatMuseumobjects"
              >
                <option value="" key="0" />
                {museumobjects
                  ? museumobjects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/collectie" replace color="info">
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

export default CollectieUpdate;
