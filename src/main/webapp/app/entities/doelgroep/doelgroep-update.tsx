import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getDoelgroeps } from 'app/entities/doelgroep/doelgroep.reducer';
import { IMuseumrelatie } from 'app/shared/model/museumrelatie.model';
import { getEntities as getMuseumrelaties } from 'app/entities/museumrelatie/museumrelatie.reducer';
import { IDoelgroep } from 'app/shared/model/doelgroep.model';
import { getEntity, updateEntity, createEntity, reset } from './doelgroep.reducer';

export const DoelgroepUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const doelgroeps = useAppSelector(state => state.doelgroep.entities);
  const museumrelaties = useAppSelector(state => state.museumrelatie.entities);
  const doelgroepEntity = useAppSelector(state => state.doelgroep.entity);
  const loading = useAppSelector(state => state.doelgroep.loading);
  const updating = useAppSelector(state => state.doelgroep.updating);
  const updateSuccess = useAppSelector(state => state.doelgroep.updateSuccess);

  const handleClose = () => {
    navigate('/doelgroep');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDoelgroeps({}));
    dispatch(getMuseumrelaties({}));
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
      ...doelgroepEntity,
      ...values,
      bestaatuitDoelgroep2: doelgroeps.find(it => it.id.toString() === values.bestaatuitDoelgroep2?.toString()),
      valtbinnenMuseumrelaties: mapIdList(values.valtbinnenMuseumrelaties),
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
          ...doelgroepEntity,
          bestaatuitDoelgroep2: doelgroepEntity?.bestaatuitDoelgroep2?.id,
          valtbinnenMuseumrelaties: doelgroepEntity?.valtbinnenMuseumrelaties?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.doelgroep.home.createOrEditLabel" data-cy="DoelgroepCreateUpdateHeading">
            Create or edit a Doelgroep
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="doelgroep-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Branch" id="doelgroep-branch" name="branch" data-cy="branch" type="text" />
              <ValidatedField label="Naam" id="doelgroep-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="doelgroep-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Segment" id="doelgroep-segment" name="segment" data-cy="segment" type="text" />
              <ValidatedField
                id="doelgroep-bestaatuitDoelgroep2"
                name="bestaatuitDoelgroep2"
                data-cy="bestaatuitDoelgroep2"
                label="Bestaatuit Doelgroep 2"
                type="select"
              >
                <option value="" key="0" />
                {doelgroeps
                  ? doelgroeps.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Valtbinnen Museumrelatie"
                id="doelgroep-valtbinnenMuseumrelatie"
                data-cy="valtbinnenMuseumrelatie"
                type="select"
                multiple
                name="valtbinnenMuseumrelaties"
              >
                <option value="" key="0" />
                {museumrelaties
                  ? museumrelaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/doelgroep" replace color="info">
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

export default DoelgroepUpdate;
