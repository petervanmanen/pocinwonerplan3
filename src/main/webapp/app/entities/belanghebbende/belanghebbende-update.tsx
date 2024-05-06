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
import { IBelanghebbende } from 'app/shared/model/belanghebbende.model';
import { getEntity, updateEntity, createEntity, reset } from './belanghebbende.reducer';

export const BelanghebbendeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const museumobjects = useAppSelector(state => state.museumobject.entities);
  const belanghebbendeEntity = useAppSelector(state => state.belanghebbende.entity);
  const loading = useAppSelector(state => state.belanghebbende.loading);
  const updating = useAppSelector(state => state.belanghebbende.updating);
  const updateSuccess = useAppSelector(state => state.belanghebbende.updateSuccess);

  const handleClose = () => {
    navigate('/belanghebbende');
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
      ...belanghebbendeEntity,
      ...values,
      heeftMuseumobjects: mapIdList(values.heeftMuseumobjects),
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
          ...belanghebbendeEntity,
          heeftMuseumobjects: belanghebbendeEntity?.heeftMuseumobjects?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.belanghebbende.home.createOrEditLabel" data-cy="BelanghebbendeCreateUpdateHeading">
            Create or edit a Belanghebbende
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
                <ValidatedField name="id" required readOnly id="belanghebbende-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datumstart" id="belanghebbende-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Datumtot" id="belanghebbende-datumtot" name="datumtot" data-cy="datumtot" type="date" />
              <ValidatedField
                label="Heeft Museumobject"
                id="belanghebbende-heeftMuseumobject"
                data-cy="heeftMuseumobject"
                type="select"
                multiple
                name="heeftMuseumobjects"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/belanghebbende" replace color="info">
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

export default BelanghebbendeUpdate;
