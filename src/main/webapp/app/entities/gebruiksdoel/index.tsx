import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gebruiksdoel from './gebruiksdoel';
import GebruiksdoelDetail from './gebruiksdoel-detail';
import GebruiksdoelUpdate from './gebruiksdoel-update';
import GebruiksdoelDeleteDialog from './gebruiksdoel-delete-dialog';

const GebruiksdoelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gebruiksdoel />} />
    <Route path="new" element={<GebruiksdoelUpdate />} />
    <Route path=":id">
      <Route index element={<GebruiksdoelDetail />} />
      <Route path="edit" element={<GebruiksdoelUpdate />} />
      <Route path="delete" element={<GebruiksdoelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GebruiksdoelRoutes;
