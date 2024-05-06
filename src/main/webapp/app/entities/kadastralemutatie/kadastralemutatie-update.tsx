import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';
import { getEntities as getRechtspersoons } from 'app/entities/rechtspersoon/rechtspersoon.reducer';
import { IKadastralemutatie } from 'app/shared/model/kadastralemutatie.model';
import { getEntity, updateEntity, createEntity, reset } from './kadastralemutatie.reducer';

export const KadastralemutatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rechtspersoons = useAppSelector(state => state.rechtspersoon.entities);
  const kadastralemutatieEntity = useAppSelector(state => state.kadastralemutatie.entity);
  const loading = useAppSelector(state => state.kadastralemutatie.loading);
  const updating = useAppSelector(state => state.kadastralemutatie.updating);
  const updateSuccess = useAppSelector(state => state.kadastralemutatie.updateSuccess);

  const handleClose = () => {
    navigate('/kadastralemutatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRechtspersoons({}));
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
      ...kadastralemutatieEntity,
      ...values,
      betrokkenenRechtspersoons: mapIdList(values.betrokkenenRechtspersoons),
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
          ...kadastralemutatieEntity,
          betrokkenenRechtspersoons: kadastralemutatieEntity?.betrokkenenRechtspersoons?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.kadastralemutatie.home.createOrEditLabel" data-cy="KadastralemutatieCreateUpdateHeading">
            Create or edit a Kadastralemutatie
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
                <ValidatedField name="id" required readOnly id="kadastralemutatie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Betrokkenen Rechtspersoon"
                id="kadastralemutatie-betrokkenenRechtspersoon"
                data-cy="betrokkenenRechtspersoon"
                type="select"
                multiple
                name="betrokkenenRechtspersoons"
              >
                <option value="" key="0" />
                {rechtspersoons
                  ? rechtspersoons.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kadastralemutatie" replace color="info">
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

export default KadastralemutatieUpdate;
