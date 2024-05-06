import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Valutasoort from './valutasoort';
import ValutasoortDetail from './valutasoort-detail';
import ValutasoortUpdate from './valutasoort-update';
import ValutasoortDeleteDialog from './valutasoort-delete-dialog';

const ValutasoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Valutasoort />} />
    <Route path="new" element={<ValutasoortUpdate />} />
    <Route path=":id">
      <Route index element={<ValutasoortDetail />} />
      <Route path="edit" element={<ValutasoortUpdate />} />
      <Route path="delete" element={<ValutasoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ValutasoortRoutes;
