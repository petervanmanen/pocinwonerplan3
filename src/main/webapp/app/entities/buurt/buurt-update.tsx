import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAreaal } from 'app/shared/model/areaal.model';
import { getEntities as getAreaals } from 'app/entities/areaal/areaal.reducer';
import { IBuurt } from 'app/shared/model/buurt.model';
import { getEntity, updateEntity, createEntity, reset } from './buurt.reducer';

export const BuurtUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const areaals = useAppSelector(state => state.areaal.entities);
  const buurtEntity = useAppSelector(state => state.buurt.entity);
  const loading = useAppSelector(state => state.buurt.loading);
  const updating = useAppSelector(state => state.buurt.updating);
  const updateSuccess = useAppSelector(state => state.buurt.updateSuccess);

  const handleClose = () => {
    navigate('/buurt');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAreaals({}));
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
      ...buurtEntity,
      ...values,
      ligtinAreaals: mapIdList(values.ligtinAreaals),
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
          ...buurtEntity,
          ligtinAreaals: buurtEntity?.ligtinAreaals?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.buurt.home.createOrEditLabel" data-cy="BuurtCreateUpdateHeading">
            Create or edit a Buurt
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="buurt-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Buurtcode" id="buurt-buurtcode" name="buurtcode" data-cy="buurtcode" type="text" />
              <ValidatedField label="Buurtgeometrie" id="buurt-buurtgeometrie" name="buurtgeometrie" data-cy="buurtgeometrie" type="text" />
              <ValidatedField label="Buurtnaam" id="buurt-buurtnaam" name="buurtnaam" data-cy="buurtnaam" type="text" />
              <ValidatedField
                label="Datumbegingeldigheidbuurt"
                id="buurt-datumbegingeldigheidbuurt"
                name="datumbegingeldigheidbuurt"
                data-cy="datumbegingeldigheidbuurt"
                type="date"
              />
              <ValidatedField label="Datumeinde" id="buurt-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumeindegeldigheidbuurt"
                id="buurt-datumeindegeldigheidbuurt"
                name="datumeindegeldigheidbuurt"
                data-cy="datumeindegeldigheidbuurt"
                type="date"
              />
              <ValidatedField label="Datumingang" id="buurt-datumingang" name="datumingang" data-cy="datumingang" type="date" />
              <ValidatedField
                label="Geconstateerd"
                id="buurt-geconstateerd"
                name="geconstateerd"
                data-cy="geconstateerd"
                check
                type="checkbox"
              />
              <ValidatedField label="Identificatie" id="buurt-identificatie" name="identificatie" data-cy="identificatie" type="text" />
              <ValidatedField label="Inonderzoek" id="buurt-inonderzoek" name="inonderzoek" data-cy="inonderzoek" check type="checkbox" />
              <ValidatedField label="Status" id="buurt-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Versie" id="buurt-versie" name="versie" data-cy="versie" type="text" />
              <ValidatedField
                label="Ligtin Areaal"
                id="buurt-ligtinAreaal"
                data-cy="ligtinAreaal"
                type="select"
                multiple
                name="ligtinAreaals"
              >
                <option value="" key="0" />
                {areaals
                  ? areaals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/buurt" replace color="info">
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

export default BuurtUpdate;
